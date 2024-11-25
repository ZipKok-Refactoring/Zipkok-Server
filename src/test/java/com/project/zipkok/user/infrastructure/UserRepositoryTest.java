package com.project.zipkok.user.infrastructure;

import com.project.zipkok.model.*;
import com.project.zipkok.repository.*;
import com.project.zipkok.user.fixture.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource("classpath:application-test.yml") //test용 properties 파일 설정
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RealEstateRepository realEstateRepository;

    @Autowired
    ZimRepository zimRepository;

    @Autowired
    KokRepository kokRepository;

    @Autowired
    StarRepository starRepository;

    @DisplayName("이메일을_통해_user_조회하기")
    @Test
    void findByEmail() {

        // given
        User expectUser = UserFixture.createDefaultUser();
        userRepository.save(expectUser);

        // when
        User foundUser = userRepository.findByEmail(expectUser.getEmail());

        // then
        assertEquals(expectUser, foundUser);
    }

    @DisplayName("user_를_조회할_때_zim_과_kok_을_조인하여_같이_조회하기")
    @Test
    void findByUserIdWithZimAndKok() {
        //given
        User expectUser = UserFixture.createDefaultUser();
        RealEstate expectRealEstate1 = RealEstateFixture.createDefaultRealEstate("예시주소1");
        RealEstate expectRealEstate2 = RealEstateFixture.createDefaultRealEstate("예시주소2");

        userRepository.save(expectUser);
        realEstateRepository.save(expectRealEstate1);
        realEstateRepository.save(expectRealEstate2);

        Zim zim1 = ZimFixture.createZim(expectUser, expectRealEstate1);
        Zim zim2 = ZimFixture.createZim(expectUser, expectRealEstate2);
        expectUser.getZims().add(zim1);
        expectUser.getZims().add(zim2);

        Kok kok1 = KokFixture.createKok(expectRealEstate1, expectUser, "디렉션내용1","리뷰내용1");
        Kok kok2 = KokFixture.createKok(expectRealEstate1, expectUser, "디렉션내용1","리뷰내용2");
        expectUser.getKoks().add(kok1);
        expectUser.getKoks().add(kok2);

        Star star1 = StarFixture.createStar(kok1);
        Star star2 = StarFixture.createStar(kok2);

        kok1.setStar(star1);
        kok2.setStar(star2);

        zimRepository.save(zim1);
        zimRepository.save(zim2);

        starRepository.save(star1);
        starRepository.save(star2);

        kokRepository.save(kok1);
        kokRepository.save(kok2);

        //when
        Optional<User> foundUserWithZimAndKok = userRepository.findByUserIdWithZimAndKok(expectUser.getUserId());

        //then
        assertTrue(foundUserWithZimAndKok.isPresent());
        User foundUser = foundUserWithZimAndKok.get();
        assertEquals(expectUser.getNickname(), foundUser.getNickname());

        // 연관된 Zim과 Kok 리스트 크기 확인
        assertThat(foundUser.getZims()).hasSize(2);
        assertThat(foundUser.getKoks()).hasSize(2);

    }

    @DisplayName("user_를_조회할_때_desire_residence_과_transaction_price_config_을_조인하여_같이_조회하기")
    @Test
    void findByUserIdWithDesireResidenceAndTransactionPriceConfig() {

        //given
        User expectUser = UserFixture.createDefaultUser();

        DesireResidence desireResidence = DesireResidenceFixture.createDefaultDesireResidence(expectUser);
        expectUser.setDesireResidence(desireResidence);

        TransactionPriceConfig transactionPriceConfig = TransactionPriceConfigFixture.createDefaultTransactionPriceConfig(expectUser);
        expectUser.setTransactionPriceConfig(transactionPriceConfig);

        userRepository.save(expectUser);

        //when
        User foundUser = userRepository.findByUserIdWithDesireResidenceAndTransactionPriceConfig(expectUser.getUserId());


        //then
        assertEquals(expectUser.getNickname(), foundUser.getNickname());
        assertThat(foundUser.getDesireResidence()).isEqualTo(desireResidence);
        assertThat(foundUser.getTransactionPriceConfig()).isEqualTo(transactionPriceConfig);
    }
}
