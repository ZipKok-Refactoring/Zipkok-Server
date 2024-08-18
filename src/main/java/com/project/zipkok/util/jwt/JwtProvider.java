package com.project.zipkok.util.jwt;

import com.project.zipkok.common.enums.Role;
import io.jsonwebtoken.*;
import com.project.zipkok.common.exception.jwt.bad_request.JwtUnsupportedTokenException;
import com.project.zipkok.common.exception.jwt.unauthorized.JwtInvalidTokenException;
import com.project.zipkok.common.exception.jwt.unauthorized.JwtMalformedTokenException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.project.zipkok.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Component
public class JwtProvider {

    @Value("${secret.jwt-secret-key}")
    private String JWT_SECRET_KEY;

    @Value("${secret.jwt-expired-in.access-token}")
    private long JWT_EXPIRED_IN;

    @Value("${secret.jwt-expired-in.refresh-token}")
    @Getter
    private long REFRESH_TOKEN_EXPIRED_IN;

    public AuthTokens createToken(JwtUserDetails jwtUserDetails) {
        log.info("JWT key={}", JWT_SECRET_KEY);

        Claims claims = Jwts.claims()
                .setSubject(jwtUserDetails.getUserId().toString())
                .setIssuer("zipkok");

        claims.put("role", jwtUserDetails.getRole().toString());

        Date now = new Date();
        Date accessTokenExpiredAt = new Date(now.getTime() + JWT_EXPIRED_IN);
        Date refreshTokenExpiredAt = new Date(now.getTime() + REFRESH_TOKEN_EXPIRED_IN);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(accessTokenExpiredAt)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(refreshTokenExpiredAt)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();

        return AuthTokens.of(accessToken, refreshToken, JWT_EXPIRED_IN, REFRESH_TOKEN_EXPIRED_IN);
    }

    public boolean isExpiredToken(String token) throws JwtInvalidTokenException {
        log.info("[JwtTokenProvider.isExpiredToken] token={}", token);
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET_KEY).build()
                    .parseClaimsJws(token);

            return claims.getBody().getExpiration().before(new Date());

        } catch (ExpiredJwtException e) {
            log.error("[ExpiredJwtException]");
            throw new JwtInvalidTokenException(EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error("[UnsupportedJwtException]");
            throw new JwtUnsupportedTokenException(UNSUPPORTED_TOKEN_TYPE);
        } catch (MalformedJwtException e) {
            log.error("[MalformedJwtException]");
            throw new JwtMalformedTokenException(MALFORMED_TOKEN);
        } catch (IllegalArgumentException e) {
            log.error("[IllegalArgumentException]");
            throw new JwtInvalidTokenException(INVALID_TOKEN);
        } catch (JwtException e) {
            log.error("[JwtTokenProvider.validateAccessToken]", e);
            throw e;
        }
    }

    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Long getId(String token) {
        return Long.valueOf(Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

    private Claims getBody(String token) {
        if (token.contains("Bearer ")) {
            token = token.substring("Bearer ".length());
        }

        return Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public JwtUserDetails getJwtUserDetails(String token) {
        Claims claims = getBody(token);
        return JwtUserDetails.builder()
                .userId(Long.valueOf(claims.getSubject()))
                .role(Role.valueOf(claims.get("role").toString()))
                .build();
    }

}