package com.project.zipkok.kok.repository;

import com.project.zipkok.dto.GetKokWithZimStatus;
import com.project.zipkok.model.*;
import com.project.zipkok.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

import static com.project.zipkok.kok.fixture.KokFixture.KOK_01;
import static com.project.zipkok.kok.fixture.RealEstateFixture.DUMMY_REALESTATE;
import static com.project.zipkok.kok.fixture.UserFixture.DUMMY_USER;
import static com.project.zipkok.kok.fixture.UserFixture.DUMMY_ZIM;
import static com.project.zipkok.kok.repository.KokRespositoryResponseMatcher.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class KokRepositoryTest {

    @Autowired
    private KokRepository kokRepository;
    @Autowired
    private ZimRepository zimRepository;

    @DisplayName("유저와 매물에 해당하는 kok 존재 여부 query 성공")
    @Test
    void 콕_존재_여부() throws Exception {
        //given
        kokRepository.save(KOK_01);
        User user = DUMMY_USER;
        RealEstate realEstate = DUMMY_REALESTATE;

        //when
        boolean response = kokRepository.existsByUserAndRealEstate(user, realEstate);

        //then
        assertTrue(response);
    }

    @DisplayName("콕 아이디 값에 일치하는 kok 반환 성공")
    @Test
    void 콕_아이디로_콕_불러오기() throws Exception {
        //given
        Kok kok = KOK_01;
        kokRepository.save(kok);

        //when
        Kok response = kokRepository.findByKokId(kok.getKokId());

        //then
        expectedRepositoryFindByKokId(response);
    }

    @DisplayName("유저 아아디 값에 일치하는 kok list 반환 성공, 무한페이징")
    @Test
    void 유저_아이디로_콕_무한페이징_불러오기() throws Exception {
        //given
        User user = DUMMY_USER;
        Kok kok = KOK_01;
        Pageable pageable = PageRequest.of(0, 10);
        kokRepository.save(kok);

        //when
        Slice<Kok> response = kokRepository.findByUserId(user.getUserId(), pageable);

        //then
        expectedRepositoryFindByUserId(response);
    }

    @DisplayName("유저 아이디 값에 일치하는 콕 list를 Zim 정보와 함께 반환 성공")
    @Test
    void 유저_아이디로_콕_무한페이징_불러오기_찜_포함() throws Exception {
        //given
        Kok kok = KOK_01;
        User user = DUMMY_USER;
        Zim zim = DUMMY_ZIM;
        Pageable pageable = PageRequest.of(0, 10);
        kokRepository.save(kok);
        zimRepository.save(zim);

        //when
        List<GetKokWithZimStatus> response = kokRepository.getKokWithZimStatus(user.getUserId(), pageable);

        //then
        expectedRepositoryGetKokWithZimStatus(response);
    }

    @DisplayName("콕 아이디를 통해 option, detailOption이 join된 콕을 반환 성공")
    @Test
    void 콕_아이디로_옵션과_세부옵션과_함께_콕_반환() throws Exception {
        //given
        Kok kok = KOK_01;
        kokRepository.save(kok);

        //when
        Kok response = kokRepository.findKokWithCheckedOptionAndCheckedDetailOption(kok.getKokId());

        //then
        expectedFindKokWithCheckedOptionAndCheckedDetailOption(response);
    }

    @DisplayName("콕 아이디를 통해 impression, star가 join된 콕을 반환 성공")
    @Test
    void 콕_아이디로_느낀점과_별점과_함께_콕_반환() throws Exception {
        //given
        Kok kok = KOK_01;
        kokRepository.save(kok);

        //when
        Kok response = kokRepository.findKokWithImpressionAndStar(kok.getKokId());

        //then
        expectedFindKokWithImpressionAndStar(response);
    }
}


