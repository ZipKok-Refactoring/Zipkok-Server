package com.project.zipkok.realEstate.infrastructure;

import com.project.zipkok.common.enums.RealEstateType;
import com.project.zipkok.common.enums.TransactionType;
import com.project.zipkok.model.RealEstate;
import com.project.zipkok.realEstate.fixture.RealEstateFixture;
import com.project.zipkok.repository.RealEstateRepository;
import com.project.zipkok.util.GeoLocationUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@DataJpaTest
public class RealEstaeRepositoryTest {

    @Autowired
    private RealEstateRepository realEstateRepository;

    @Test
    @DisplayName("RealEstate 저장 테스트")
    public void saveTest() {
        // given
        RealEstate newRealEstate = RealEstate.builder()
                .address("ADDRESS")
                .transactionType(TransactionType.MONTHLY)
                .realEstateType(RealEstateType.ONEROOM)
                .deposit(1000L)
                .price(50L)
                .administrativeFee(120000)
                .latitude(1.0)
                .longitude(1.0)
                .agent("AGENT")
                .imageUrl("https://testThumbnailImage.com")
                .build();

        // when
        RealEstate savedRealEstate = realEstateRepository.save(newRealEstate);

        // then
        assertThat(savedRealEstate).isNotNull();
        assertThat(savedRealEstate.getRealEstateId()).isNotNull();
        assertThat(savedRealEstate.getRealEstateId()).isEqualTo(newRealEstate.getRealEstateId());
    }

    @Test
    @DisplayName("저장 후 id로 조회 테스트")
    public void findByIdTest() {
        //given
        RealEstate newRealEstate = RealEstate.builder()
                .address("ADDRESS")
                .transactionType(TransactionType.MONTHLY)
                .realEstateType(RealEstateType.ONEROOM)
                .deposit(1000L)
                .price(50L)
                .administrativeFee(120000)
                .latitude(1.0)
                .longitude(1.0)
                .agent("AGENT")
                .imageUrl("https://testThumbnailImage.com")
                .build();

        RealEstate savedRealEstate = realEstateRepository.save(newRealEstate);

        //when
        RealEstate findRealEstate = realEstateRepository.findById(savedRealEstate.getRealEstateId().longValue()).orElseGet(null);

        //then
        assertThat(findRealEstate).isNotNull();
        assertThat(findRealEstate.getRealEstateId()).isEqualTo(savedRealEstate.getRealEstateId());
    }

    @Test
    @DisplayName("위도 경도 범위로 조회 테스트")
    public void findByLatitudeBetweenAndLongitudeBetweenTest() {

        //given
        realEstateRepository.save(
                RealEstateFixture.makeTestRealEstateWithLatLon(null, TransactionType.MONTHLY, RealEstateType.ONEROOM, 1000L, 48L, 1.0, 1.0)
        );
        realEstateRepository.save(
                RealEstateFixture.makeTestRealEstateWithLatLon(null, TransactionType.MONTHLY, RealEstateType.ONEROOM, 1000L, 48L, 1.1, 1.1)
        );
        realEstateRepository.save(
                RealEstateFixture.makeTestRealEstateWithLatLon(null, TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 48L, 1.2, 1.2)
        );
        realEstateRepository.save(
                RealEstateFixture.makeTestRealEstateWithLatLon(null, TransactionType.YEARLY, RealEstateType.ONEROOM, 1000L, 48L, 1.3, 1.3)
        );
        realEstateRepository.save(
                RealEstateFixture.makeTestRealEstateWithLatLon(null, TransactionType.YEARLY, RealEstateType.ONEROOM, 1000L, 48L, 1.4, 1.4)
        );
        realEstateRepository.save(
                RealEstateFixture.makeTestRealEstateWithLatLon(null, TransactionType.YEARLY, RealEstateType.APARTMENT, 1000L, 48L, 1.5, 1.5)
        );

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
        realEstateRepository.save(
                RealEstateFixture.makeTestRealEstateWithLatLon(null, TransactionType.MONTHLY, RealEstateType.ONEROOM, 1000L, 48L, 1.0, 1.0)
        );
        realEstateRepository.save(
                RealEstateFixture.makeTestRealEstateWithLatLon(null, TransactionType.MONTHLY, RealEstateType.ONEROOM, 1000L, 48L, 1.1, 1.1)
        );
        realEstateRepository.save(
                RealEstateFixture.makeTestRealEstateWithLatLon(null, TransactionType.MONTHLY, RealEstateType.APARTMENT, 1000L, 48L, 1.2, 1.2)
        );
        realEstateRepository.save(
                RealEstateFixture.makeTestRealEstateWithLatLon(null, TransactionType.YEARLY, RealEstateType.ONEROOM, 1000L, 48L, 1.3, 1.3)
        );
        realEstateRepository.save(
                RealEstateFixture.makeTestRealEstateWithLatLon(null, TransactionType.YEARLY, RealEstateType.ONEROOM, 1000L, 48L, 1.4, 1.4)
        );
        realEstateRepository.save(
                RealEstateFixture.makeTestRealEstateWithLatLon(null, TransactionType.YEARLY, RealEstateType.APARTMENT, 1000L, 48L, 1.5, 1.5)
        );
        realEstateRepository.save(
                RealEstateFixture.makeTestRealEstateWithLatLon(null, TransactionType.YEARLY, RealEstateType.TWOROOM, 1000L, 48L, 1.6, 1.6)
        );

        double curLatitude = 1.3;
        double curLongitude = 1.3;
        double radiusInKm = 22;
        double[] bounds = GeoLocationUtils.getSquareBounds(curLatitude, curLongitude, radiusInKm);

        for (int i = 0; i < bounds.length; i++) {
            log.info("bounds[{}] : {}", i, bounds[i]);
        }

        realEstateRepository.findAll().stream().forEach(realEstate -> {
            log.info("realEstate: id: {}, lat: {}, lon: {}, imageSize: {}", realEstate.getRealEstateId(), realEstate.getLatitude(), realEstate.getLongitude(), realEstate.getRealEstateImages().size() );
        });

        //when
        List<RealEstate> result = realEstateRepository.findTop5ByLatitudeBetweenAndLongitudeBetween(curLatitude, curLongitude, bounds[0], bounds[1], bounds[2], bounds[3]);

        //then
        assertEquals(4, result.size());
    }

}
