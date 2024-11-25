package com.project.zipkok.user.fixture.domain;

import com.project.zipkok.common.enums.Gender;
import com.project.zipkok.common.enums.OAuthProvider;
import com.project.zipkok.common.enums.Role;
import com.project.zipkok.model.DesireResidence;
import com.project.zipkok.model.TransactionPriceConfig;
import com.project.zipkok.model.User;
import jdk.jshell.Snippet;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class UserFixture {
    public static User createDefaultUser() {
//        return User.builder()
//                .email("test@example.com")
//                .oAuthProvider(OAuthProvider.KAKAO)
//                .nickname("TestUser")
//                .gender(Gender.MALE)
//                .birthday("19970609")
//                .status("active")
//                .role(Role.USER)
//                .zims(new LinkedHashSet<>())
//                .koks(new LinkedHashSet<>())
//                .build();

        return createCustomUser("test@example.com","TestUser", "19970609");
    }

    public static User createCustomUser(String email, String nickname, String birthday) {
        return User.builder()
                .email(email)
                .oAuthProvider(OAuthProvider.KAKAO)
                .nickname(nickname)
                .gender(Gender.MALE)
                .birthday(birthday)
                .role(Role.USER)
                .koks(new HashSet<>())
                .zims(new HashSet<>())
                .status("active")
                .build();
    }

    public static User createUserWithDetails() {
        User user = createDefaultUser();

        DesireResidence desireResidence = DesireResidenceFixture.createDefaultDesireResidence(user);
        TransactionPriceConfig transactionPriceConfig = TransactionPriceConfigFixture.createDefaultTransactionPriceConfig(user);

        user.setDesireResidence(desireResidence);
        user.setTransactionPriceConfig(transactionPriceConfig);

        return user;
    }
}
