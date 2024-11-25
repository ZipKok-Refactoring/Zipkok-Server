package com.project.zipkok.util.jwt;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthTokens {

    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private Long refreshTokenExpiresIn;

    public static AuthTokens of(String accessToken, String refreshToken, Long expiresIn, Long refreshTokenExpiresIn) {
        return new AuthTokens(accessToken, refreshToken, expiresIn, refreshTokenExpiresIn);
    }
}
