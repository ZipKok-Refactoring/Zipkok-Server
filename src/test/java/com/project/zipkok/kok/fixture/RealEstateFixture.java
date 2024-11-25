package com.project.zipkok.kok.fixture;

import com.project.zipkok.model.RealEstate;
import com.project.zipkok.model.RealEstateImage;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static com.project.zipkok.common.enums.RealEstateType.ONEROOM;
import static com.project.zipkok.common.enums.TransactionType.MONTHLY;

public class RealEstateFixture {

    public static final RealEstate DUMMY_REALESTATE = RealEstate.builder()
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
            .imageUrl("test/test")
            .build();

    public static final RealEstateImage DUMMY_REALESATE_IMAGE = new RealEstateImage("https://test.com", DUMMY_REALESTATE);

    static {
        ReflectionTestUtils.setField(DUMMY_REALESTATE, "realEstateId", 1L);
        ReflectionTestUtils.setField(DUMMY_REALESTATE, "realEstateImages", List.of(DUMMY_REALESATE_IMAGE));
    }
}
