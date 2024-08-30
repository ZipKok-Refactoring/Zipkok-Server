package com.project.zipkok.realEstate.fixture;

import com.project.zipkok.common.enums.Gender;
import com.project.zipkok.common.enums.OAuthProvider;
import com.project.zipkok.common.enums.Role;
import com.project.zipkok.model.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Set;

import static com.project.zipkok.common.enums.RealEstateType.APARTMENT;
import static com.project.zipkok.common.enums.RealEstateType.ONEROOM;
import static com.project.zipkok.common.enums.TransactionType.*;

public class RealEstateFixture {

    public static final RealEstate MONTHLY_ONEROOM_01 = RealEstate.builder()
            .address("MONTHLY_ONEROOM_01_ADDRESS")
            .transactionType(MONTHLY)
            .realEstateType(ONEROOM)
            .deposit(1000L)
            .price(48L)
            .administrativeFee(120000)
            .latitude(1.0)
            .longitude(1.0)
            .agent("MONTHLY_ONEROOM_01_AGENT")
            .imageUrl("https://MONTHLY_ONEROOM_01image.com")
            .build();

    public static final RealEstate MONTHLY_ONEROOM_02 = RealEstate.builder()
            .address("MONTHLY_ONEROOM_02_ADDRESS")
            .transactionType(MONTHLY)
            .realEstateType(ONEROOM)
            .deposit(1000000L)
            .price(50000L)
            .administrativeFee(20000)
            .latitude(1.1)
            .longitude(1.1)
            .agent("MONTHLY_ONEROOM_02_AGENT")
            .imageUrl("https://MONTHLY_ONEROOM_02image.com")
            .build();

    public static final RealEstate MONTHLY_APARTMENT_01 = RealEstate.builder()
            .address("MONTHLY_APARTMENT_01_ADDRESS")
            .transactionType(MONTHLY)
            .realEstateType(APARTMENT)
            .deposit(1000L)
            .price(50L)
            .administrativeFee(20000)
            .latitude(1.2)
            .longitude(1.2)
            .agent("MONTHLY_APARTMENT_01_AGENT")
            .imageUrl("https://MONTHLY_APARTMENT_01image.com")
            .build();

    public static final RealEstate YEARLY_ONEROOM_01 = RealEstate.builder()
            .address("YEARLY_ONEROOM_01_ADDRESS")
            .transactionType(YEARLY)
            .realEstateType(ONEROOM)
            .deposit(20000L)
            .administrativeFee(50000)
            .latitude(1.3)
            .longitude(1.3)
            .agent("YEARLY_ONEROOM_01_AGENT")
            .imageUrl("https://YEARLY_ONEROOM_01image.com")
            .build();

    public static final RealEstate YEARLY_ONEROOM_02 = RealEstate.builder()
            .address("YEARLY_ONEROOM_02_ADDRESS")
            .transactionType(YEARLY)
            .realEstateType(ONEROOM)
            .deposit(30000L)
            .administrativeFee(20000)
            .latitude(1.4)
            .longitude(1.4)
            .agent("YEARLY_ONEROOM_02_AGENT")
            .imageUrl("https://YEARLY_ONEROOM_02image.com")
            .build();

    public static final RealEstate YEARLY_APARTMENT_01 = RealEstate.builder()
            .address("YEARLY_APARTMENT_01_ADDRESS")
            .transactionType(YEARLY)
            .realEstateType(APARTMENT)
            .deposit(40000L)
            .administrativeFee(100000)
            .latitude(1.5)
            .longitude(1.5)
            .agent("YEARLY_APARTMENT_01_AGENT")
            .imageUrl("https://YEARLY_APARTMENT_01image.com")
            .build();

    public static final RealEstate YEARLY_APARTMENT_02 = RealEstate.builder()
            .address("YEARLY_APARTMENT_02_ADDRESS")
            .transactionType(YEARLY)
            .realEstateType(APARTMENT)
            .deposit(70000L)
            .administrativeFee(120000)
            .latitude(1.6)
            .longitude(1.6)
            .agent("YEARLY_APARTMENT_02_AGENT")
            .imageUrl("https://YEARLY_APARTMENT_02image.com")
            .build();

    public static final RealEstate PURCHASE_ONEROOM_01 = RealEstate.builder()
            .address("PURCHASE_ONEROOM_01_ADDRESS")
            .transactionType(PURCHASE)
            .realEstateType(ONEROOM)
            .deposit(45000L)
            .latitude(1.7)
            .longitude(1.7)
            .agent("PURCHASE_ONEROOM_01_AGENT")
            .imageUrl("https://PURCHASE_ONEROOM_01image.com")
            .build();

    public static final RealEstate PURCHASE_APARTMENT_02 = RealEstate.builder()
            .address("PURCHASE_APARTMENT_02_ADDRESS")
            .transactionType(PURCHASE)
            .realEstateType(APARTMENT)
            .deposit(80000L)
            .latitude(1.8)
            .longitude(1.8)
            .agent("PURCHASE_APARTMENT_02_AGENT")
            .imageUrl("https://PURCHASE_APARTMENT_02image.com")
            .build();

    public static final RealEstateImage DUMMY_REAL_ESTATE_IMAGE = new RealEstateImage("https://DUMMY_REAL_ESTATE_IMAGE.com", MONTHLY_ONEROOM_01);

    public static final DesireResidence DUMMY_DESIRE_RESIDENCE = new DesireResidence("user_address", 0.5, 0.5);
    public static final TransactionPriceConfig DUMMY_TRANSACTION_PRICE_CONFIG = new TransactionPriceConfig(0L, 800000L, 0L, 200000000L, 0L, 800000000L, 0L, 1000000000L);

    public static final User USER_USER = User.builder()
            .nickname("USER_USER")
            .oAuthProvider(OAuthProvider.KAKAO)
            .desireResidence(DUMMY_DESIRE_RESIDENCE)
            .transactionPriceConfig(DUMMY_TRANSACTION_PRICE_CONFIG)
            .transactionType(MONTHLY)
            .realEstateType(ONEROOM)
            .role(Role.USER)
            .birthday("000101")
            .gender(Gender.MALE)
            .email("user@kakao.com")
            .status("active")
            .build();

    public static final Zim DUMMY_ZIM = new Zim(MONTHLY_ONEROOM_01, USER_USER);
    public static final Kok DUMMY_KOK = new Kok(MONTHLY_ONEROOM_01,"This is Dummy Review", USER_USER);

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

        ReflectionTestUtils.setField(DUMMY_REAL_ESTATE_IMAGE, "realEstateImgId", 1L);
        ReflectionTestUtils.setField(MONTHLY_ONEROOM_01, "realEstateImages", List.of(DUMMY_REAL_ESTATE_IMAGE));

        ReflectionTestUtils.setField(DUMMY_DESIRE_RESIDENCE, "desireResidenceId", 1L);
        ReflectionTestUtils.setField(DUMMY_TRANSACTION_PRICE_CONFIG, "transactionConfigId", 1L);

        ReflectionTestUtils.setField(USER_USER, "userId", 1L);

        ReflectionTestUtils.setField(DUMMY_ZIM, "zimId", 1L);
        ReflectionTestUtils.setField(DUMMY_KOK, "kokId", 1L);

        ReflectionTestUtils.setField(USER_USER, "zims", Set.of(DUMMY_ZIM));
        ReflectionTestUtils.setField(USER_USER, "koks", Set.of(DUMMY_KOK));
    }
}
