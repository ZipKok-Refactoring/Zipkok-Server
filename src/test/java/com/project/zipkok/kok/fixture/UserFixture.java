package com.project.zipkok.kok.fixture;

import com.project.zipkok.common.enums.Gender;
import com.project.zipkok.common.enums.OAuthProvider;
import com.project.zipkok.common.enums.Role;
import com.project.zipkok.model.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Set;

import static com.project.zipkok.common.enums.RealEstateType.ONEROOM;
import static com.project.zipkok.common.enums.TransactionType.MONTHLY;
import static com.project.zipkok.kok.fixture.CheckedFixture.*;
import static com.project.zipkok.kok.fixture.KokFixture.KOK_01;
import static com.project.zipkok.kok.fixture.RealEstateFixture.DUMMY_REALSTATE;

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
            .highlights(Set.of(DUMMY_HIGHLIGHT))
            .options(Set.of(DUMMY_OPTION_OUTER, DUMMY_OPTION_INNER, DUMMY_OPTION_CONTRACT))
            .impressions(Set.of(DUMMY_IMPRESSION))
            .koks(Set.of(KOK_01))
            .build();

    public static final Zim DUMMY_ZIM = new Zim(DUMMY_REALSTATE, DUMMY_USER);
    public static final Kok DUMMY_KOK = new Kok(DUMMY_REALSTATE,"This is Dummy Review", DUMMY_USER);

    static {
        ReflectionTestUtils.setField(DUMMY_DESIRE_RESIDENCE, "desireResidenceId", 1L);
        ReflectionTestUtils.setField(DUMMY_TRANSACTION_PRICE_CONFIG, "transactionConfigId", 1L);
        ReflectionTestUtils.setField(DUMMY_ZIM, "zimId", 1L);
        ReflectionTestUtils.setField(DUMMY_KOK, "kokId", 1L);

        ReflectionTestUtils.setField(DUMMY_USER, "userId", 1L);
        ReflectionTestUtils.setField(DUMMY_USER, "zims", Set.of(DUMMY_ZIM));
        ReflectionTestUtils.setField(DUMMY_USER, "koks", Set.of(DUMMY_KOK));
    }
}
