package com.project.zipkok.service;

import com.project.zipkok.common.exception.jwt.unauthorized.JwtInvalidRefreshTokenException;
import com.project.zipkok.common.exception.user.NoMatchUserException;
import com.project.zipkok.common.service.RedisService;
import com.project.zipkok.dto.PostRefreshTokenResponse;
import com.project.zipkok.model.User;
import com.project.zipkok.repository.UserRepository;
import com.project.zipkok.util.jwt.AuthTokens;
import com.project.zipkok.util.jwt.JwtProvider;
import com.project.zipkok.util.jwt.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.project.zipkok.common.response.status.BaseExceptionResponseStatus.INVALID_REFRESH_TOKEN;
import static com.project.zipkok.common.response.status.BaseExceptionResponseStatus.MEMBER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final RedisService redisService;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;


    public PostRefreshTokenResponse reIssueToken(String refreshToken) {
        log.info("AuthService.reIssueToken");

        jwtProvider.isExpiredToken(refreshToken);

        User user = userRepository.findById(jwtProvider.getId(refreshToken))
                .orElseThrow(() -> new NoMatchUserException(MEMBER_NOT_FOUND));

        String email = user.getEmail();

        if (!redisService.getValues(email).equals(refreshToken)) {
            log.error("[\nemail : " + email + "\nredis token : " + redisService.getValues(email) + "\nrequest token : " + refreshToken + "]");
            throw new JwtInvalidRefreshTokenException(INVALID_REFRESH_TOKEN);
        }

        AuthTokens authTokens = jwtProvider.createToken(JwtUserDetails.from(user));

        redisService.setValues(email, authTokens.getRefreshToken(), Duration.ofMillis(jwtProvider.getREFRESH_TOKEN_EXPIRED_IN()));

        return new PostRefreshTokenResponse(authTokens);
    }
}
