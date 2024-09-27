package com.project.zipkok.realEstate.fixture;

import com.project.zipkok.common.enums.*;
import com.project.zipkok.model.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Set;

import static com.project.zipkok.common.enums.RealEstateType.APARTMENT;
import static com.project.zipkok.common.enums.RealEstateType.ONEROOM;
import static com.project.zipkok.common.enums.TransactionType.*;

public class RealEstateFixture {

    public static RealEstate makeTestRealEstate(Long id, TransactionType transactionType, RealEstateType realEstateType, Long deposit, Long price) {

        RealEstate realEstate = RealEstate.builder()
                .address("ADDRESS")
                .transactionType(transactionType)
                .realEstateType(realEstateType)
                .deposit(deposit)
                .price(price)
                .administrativeFee(120000)
                .latitude(1.0)
                .longitude(1.0)
                .agent("AGENT")
                .imageUrl("https://testThumbnailImage.com")
                .build();

        ReflectionTestUtils.setField(realEstate, "realEstateId", id);

        return realEstate;
    }

    public static RealEstate makeTestRealEstateWithLatLon(Long id, TransactionType transactionType, RealEstateType realEstateType, Long deposit, Long price, Double latitude, Double longitude) {
        RealEstate realEstate = RealEstate.builder()
                .address("ADDRESS")
                .transactionType(transactionType)
                .realEstateType(realEstateType)
                .deposit(deposit)
                .price(price)
                .administrativeFee(120000)
                .latitude(latitude)
                .longitude(longitude)
                .agent("AGENT")
                .imageUrl("https://testImage.com")
                .build();

        ReflectionTestUtils.setField(realEstate, "realEstateId", id);

        return realEstate;
    }

    public static RealEstate makeTestRealEstateWithRealEstateImage(Long id, TransactionType transactionType, RealEstateType realEstateType, Long deposit, Long price) {

        RealEstate realEstate = RealEstate.builder()
                .address("ADDRESS")
                .transactionType(transactionType)
                .realEstateType(realEstateType)
                .deposit(deposit)
                .price(price)
                .administrativeFee(120000)
                .latitude(1.0)
                .longitude(1.0)
                .agent("AGENT")
                .imageUrl("https://testThumbnailImage.com")
                .build();

        RealEstateImage realEstateImage = new RealEstateImage("https://testRealEstateImage.com", realEstate);

        ReflectionTestUtils.setField(realEstate, "realEstateId", id);
        ReflectionTestUtils.setField(realEstateImage, "realEstateImgId", 1L);
        ReflectionTestUtils.setField(realEstate, "realEstateImages", List.of(realEstateImage));

        return realEstate;
    }

    public static RealEstate makeDummyRealEstate(Long id) {
        RealEstate realEstate = RealEstate.builder().build();
        ReflectionTestUtils.setField(realEstate, "realEstateId", id);
        return realEstate;
    }

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
            .deposit(10000000L)
            .price(500000L)
            .administrativeFee(20000)
            .latitude(1.1)
            .longitude(1.1)
            .agent("MONTHLY_ONEROOM_02_AGENT")
            .imageUrl("https://MONTHLY_ONEROOM_02image.com")
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
