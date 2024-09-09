package com.project.zipkok.kok.fixture;

import com.project.zipkok.model.RealEstate;
import org.springframework.test.util.ReflectionTestUtils;

import static com.project.zipkok.common.enums.RealEstateType.APARTMENT;
import static com.project.zipkok.common.enums.RealEstateType.ONEROOM;
import static com.project.zipkok.common.enums.TransactionType.MONTHLY;
import static com.project.zipkok.common.enums.TransactionType.PURCHASE;

public class RealEstateFixture {

    public static final RealEstate DUMMY_REALSTATE = RealEstate.builder()
            .address("PURCHASE_APARTMENT_02_ADDRESS")
            .transactionType(MONTHLY)
            .realEstateType(ONEROOM)
            .deposit(100000L)
            .latitude(127.1)
            .longitude(127.1)
            .agent("RealEstate_01_AGENT")
            .imageUrl("https://RealEstate_01image.com")
            .address("test/test")
            .detailAddress("101")
            .detail("테스트입니다")
            .areaSize(1.1f)
            .pyeongsu(5)
            .floorNum(2)
            .administrativeFee(5000)
            .price(500)
            .build();

    static {
        ReflectionTestUtils.setField(DUMMY_REALSTATE, "realEstateId", 1L);
    }
}
