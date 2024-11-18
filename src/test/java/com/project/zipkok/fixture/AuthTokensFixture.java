package com.project.zipkok.fixture;

import com.project.zipkok.util.jwt.AuthTokens;

public class AuthTokensFixture {

    public static AuthTokens createDefaultAuthTokens() {
        return AuthTokens.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .expiresIn(1000L)
                .refreshTokenExpiresIn(5000L)
                .build();
    }
}
