package com.project.zipkok.realEstate.fixture;

import com.project.zipkok.model.RealEstate;
import org.springframework.test.util.ReflectionTestUtils;

import static com.project.zipkok.common.enums.RealEstateType.APARTMENT;
import static com.project.zipkok.common.enums.RealEstateType.ONEROOM;
import static com.project.zipkok.common.enums.TransactionType.*;

public class RealEstateFixture {

    public static final RealEstate MONTHLY_ONEROOM_01 = RealEstate.builder()
            .address("MONTHLY_ONEROOM_01_ADDRESS")
            .transactionType(MONTHLY)
            .realEstateType(ONEROOM)
            .deposit(10000000L)
            .price(480000L)
            .administrativeFee(120000)
            .latitude(37.123456)
            .longitude(127.123456)
            .agent("MONTHLY_ONEROOM_01_AGENT")
            .build();

    public static final RealEstate MONTHLY_ONEROOM_02 = RealEstate.builder()
            .address("MONTHLY_ONEROOM_02_ADDRESS")
            .transactionType(MONTHLY)
            .realEstateType(ONEROOM)
            .deposit(10000000L)
            .price(500000L)
            .administrativeFee(20000)
            .latitude(37.123457)
            .longitude(127.123356)
            .agent("MONTHLY_ONEROOM_02_AGENT")
            .build();

    public static final RealEstate MONTHLY_APARTMENT_01 = RealEstate.builder()
            .address("MONTHLY_APARTMENT_01_ADDRESS")
            .transactionType(MONTHLY)
            .realEstateType(APARTMENT)
            .deposit(10000000L)
            .price(500000L)
            .administrativeFee(20000)
            .latitude(37.123456)
            .longitude(127.123456)
            .agent("MONTHLY_APARTMENT_01_AGENT")
            .build();

    public static final RealEstate YEARLY_ONEROOM_01 = RealEstate.builder()
            .address("YEARLY_ONEROOM_01_ADDRESS")
            .transactionType(YEARLY)
            .realEstateType(ONEROOM)
            .deposit(200000000L)
            .administrativeFee(50000)
            .latitude(37.143456)
            .longitude(127.123256)
            .agent("YEARLY_ONEROOM_01_AGENT")
            .build();

    public static final RealEstate YEARLY_ONEROOM_02 = RealEstate.builder()
            .address("YEARLY_ONEROOM_02_ADDRESS")
            .transactionType(YEARLY)
            .realEstateType(ONEROOM)
            .deposit(300000000L)
            .administrativeFee(20000)
            .latitude(37.143406)
            .longitude(127.123156)
            .agent("YEARLY_ONEROOM_02_AGENT")
            .build();

    public static final RealEstate YEARLY_APARTMENT_01 = RealEstate.builder()
            .address("YEARLY_APARTMENT_01_ADDRESS")
            .transactionType(YEARLY)
            .realEstateType(APARTMENT)
            .deposit(400000000L)
            .administrativeFee(100000)
            .latitude(37.123446)
            .longitude(127.123456)
            .agent("YEARLY_APARTMENT_01_AGENT")
            .build();

    public static final RealEstate YEARLY_APARTMENT_02 = RealEstate.builder()
            .address("YEARLY_APARTMENT_02_ADDRESS")
            .transactionType(YEARLY)
            .realEstateType(APARTMENT)
            .deposit(700000000L)
            .administrativeFee(120000)
            .latitude(37.122446)
            .longitude(127.123459)
            .agent("YEARLY_APARTMENT_02_AGENT")
            .build();

    public static final RealEstate PURCHASE_ONEROOM_01 = RealEstate.builder()
            .address("PURCHASE_ONEROOM_01_ADDRESS")
            .transactionType(PURCHASE)
            .realEstateType(ONEROOM)
            .deposit(450000000L)
            .latitude(37.1434577)
            .longitude(127.12325644)
            .agent("PURCHASE_ONEROOM_01_AGENT")
            .build();

    public static final RealEstate PURCHASE_APARTMENT_02 = RealEstate.builder()
            .address("PURCHASE_APARTMENT_02_ADDRESS")
            .transactionType(PURCHASE)
            .realEstateType(APARTMENT)
            .deposit(800000000L)
            .latitude(37.1434577)
            .longitude(127.12325644)
            .agent("PURCHASE_APARTMENT_02_AGENT")
            .build();

    static {
        ReflectionTestUtils.setField(MONTHLY_ONEROOM_01, "realEstateId", 1L);
        ReflectionTestUtils.setField(MONTHLY_ONEROOM_02, "realEstateId", 2L);
        ReflectionTestUtils.setField(MONTHLY_APARTMENT_01, "realEstateId", 3L);
        ReflectionTestUtils.setField(YEARLY_ONEROOM_01, "realEstateId", 4L);
        ReflectionTestUtils.setField(YEARLY_ONEROOM_02, "realEstateId", 5L);
        ReflectionTestUtils.setField(YEARLY_APARTMENT_01, "realEstateId", 6L);
        ReflectionTestUtils.setField(YEARLY_APARTMENT_02, "realEstateId", 7L);
        ReflectionTestUtils.setField(PURCHASE_ONEROOM_01, "realEstateId", 8L);
        ReflectionTestUtils.setField(PURCHASE_APARTMENT_02, "realEstateId", 9L);
    }
}
