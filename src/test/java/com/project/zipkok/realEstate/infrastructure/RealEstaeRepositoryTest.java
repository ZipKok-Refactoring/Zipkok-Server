package com.project.zipkok.realEstate.infrastructure;

import com.project.zipkok.model.RealEstate;
import com.project.zipkok.realEstate.fixture.RealEstateFixture;
import com.project.zipkok.repository.RealEstateRepository;
import com.project.zipkok.util.GeoLocationUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Slf4j
public class RealEstaeRepositoryTest {

    @Autowired
    private RealEstateRepository realEstateRepository;

    @Test
    @DisplayName("RealEstate 저장 후 조회 테스트")
    @Order(0)
    public void saveTest() {
        // given
        RealEstate newRealEstate = RealEstateFixture.MONTHLY_APARTMENT_01;

        // when
        RealEstate savedRealEstate = realEstateRepository.save(newRealEstate);

        // then
        assertThat(savedRealEstate).isNotNull();
        assertThat(savedRealEstate.getRealEstateId()).isNotNull();
        assertThat(savedRealEstate.getRealEstateId()).isEqualTo(newRealEstate.getRealEstateId());

        RealEstate findRealEstate = realEstateRepository.findById(savedRealEstate.getRealEstateId()).orElseGet(null);

        assertThat(findRealEstate).isNotNull();
        assertThat(findRealEstate.getRealEstateId()).isEqualTo(savedRealEstate.getRealEstateId());
    }

    @Test
    @DisplayName("id로 조회 테스트")
    public void findByIdTest() {
        //given
        realEstateRepository.save(RealEstateFixture.MONTHLY_ONEROOM_01);

        //when
        RealEstate findRealEstate = realEstateRepository.findById(RealEstateFixture.MONTHLY_ONEROOM_01.getRealEstateId().longValue()).orElseGet(null);

        //then
        assertThat(findRealEstate).isNotNull();
        assertThat(findRealEstate.getRealEstateId()).isEqualTo(RealEstateFixture.MONTHLY_ONEROOM_01.getRealEstateId());
    }

    @Test
    @DisplayName("위도 경도 범위로 조회 테스트")
    public void findByLatitudeBetweenAndLongitudeBetweenTest() {

        //given
        realEstateRepository.save(RealEstateFixture.MONTHLY_ONEROOM_01);
        realEstateRepository.save(RealEstateFixture.MONTHLY_ONEROOM_02);
        realEstateRepository.save(RealEstateFixture.MONTHLY_APARTMENT_01);
        realEstateRepository.save(RealEstateFixture.YEARLY_ONEROOM_01);
        realEstateRepository.save(RealEstateFixture.YEARLY_ONEROOM_02);
        realEstateRepository.save(RealEstateFixture.YEARLY_APARTMENT_01);
        realEstateRepository.save(RealEstateFixture.PURCHASE_APARTMENT_02);
        realEstateRepository.save(RealEstateFixture.PURCHASE_ONEROOM_01);

        double minLatitude = 0;
        double minLongitude = 0;

        double maxLatitude = 1.3;
        double maxLongitude = 1.3;

        //when
        List<RealEstate> result = realEstateRepository.findByLatitudeBetweenAndLongitudeBetween(minLatitude, maxLatitude, minLongitude, maxLongitude);

        //then
        assertEquals(4, result.size());
    }

    @Test
    @DisplayName("근처 매물조회 테스트")
    public void findTop5ByLatitudeBetweenAndLongitudeBetweenTest() {
        //given
        realEstateRepository.save(RealEstateFixture.MONTHLY_ONEROOM_01);
        realEstateRepository.save(RealEstateFixture.MONTHLY_ONEROOM_02);
        realEstateRepository.save(RealEstateFixture.MONTHLY_APARTMENT_01);
        realEstateRepository.save(RealEstateFixture.YEARLY_ONEROOM_01);
        realEstateRepository.save(RealEstateFixture.YEARLY_ONEROOM_02);
        realEstateRepository.save(RealEstateFixture.YEARLY_APARTMENT_01);
        realEstateRepository.save(RealEstateFixture.PURCHASE_APARTMENT_02);
        realEstateRepository.save(RealEstateFixture.PURCHASE_ONEROOM_01);
        realEstateRepository.save(RealEstateFixture.YEARLY_APARTMENT_02);

        double curLatitude = 1.2;
        double curLongitude = 1.2;
        double radiusInKm = 22;
        double[] bounds = GeoLocationUtils.getSquareBounds(curLatitude, curLongitude, radiusInKm);

        //when
        List<RealEstate> result = realEstateRepository.findTop5ByLatitudeBetweenAndLongitudeBetween(curLatitude, curLongitude, bounds[0], bounds[1], bounds[2], bounds[3]);

        //then
        assertEquals(4, result.size());
    }

}
