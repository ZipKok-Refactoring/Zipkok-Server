package com.project.zipkok.kok.repository;

import com.project.zipkok.model.Kok;
import com.project.zipkok.model.RealEstate;
import com.project.zipkok.model.User;
import com.project.zipkok.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.project.zipkok.kok.fixture.KokFixture.KOK_01;
import static com.project.zipkok.kok.fixture.RealEstateFixture.DUMMY_REALESTATE;
import static com.project.zipkok.kok.fixture.UserFixture.DUMMY_USER;

@DataJpaTest
public class KokRepositoryTest {
    @Autowired
    private KokRepository kokRepository;

    @DisplayName("유저와 매물에 해당하는 kok 존재 여부 query 성공")
    @Test
    void 콕_존재_여부() throws Exception {
        //given
        Kok kok = KOK_01;
        User user = DUMMY_USER;
        RealEstate realEstate = DUMMY_REALESTATE;

        //when
    }
}
