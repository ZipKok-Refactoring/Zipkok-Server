package com.project.zipkok.kok.fixture;

import com.project.zipkok.common.enums.Gender;
import com.project.zipkok.common.enums.OAuthProvider;
import com.project.zipkok.common.enums.Role;
import com.project.zipkok.model.DesireResidence;
import com.project.zipkok.model.TransactionPriceConfig;
import com.project.zipkok.model.User;
import org.springframework.test.util.ReflectionTestUtils;

import static com.project.zipkok.common.enums.RealEstateType.ONEROOM;
import static com.project.zipkok.common.enums.TransactionType.MONTHLY;

public class UserFixture {

    public static final DesireResidence DUMMY_DESIRE_RESIDENCE = new DesireResidence("user_address", 0.5, 0.5);
    public static final TransactionPriceConfig DUMMY_TRANSACTION_PRICE_CONFIG = new TransactionPriceConfig(0L, 800000L, 0L, 200000000L, 0L, 800000000L, 0L, 1000000000L);

    public static final User DUMMY_USER = User.builder()
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

    static {
        ReflectionTestUtils.setField(DUMMY_DESIRE_RESIDENCE, "desireResidenceId", 1L);
        ReflectionTestUtils.setField(DUMMY_TRANSACTION_PRICE_CONFIG, "transactionConfigId", 1L);

        ReflectionTestUtils.setField(DUMMY_USER, "userId", 1L);
    }
}
