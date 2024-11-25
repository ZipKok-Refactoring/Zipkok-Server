package com.project.zipkok.util.security;

import com.project.zipkok.util.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationUtil {

    private final JwtProvider jwtProvider;

    public void setAuthenticationFromRequest(HttpServletRequest request) {

        log.info("[AuthenticationUtil.setAuthenticationFromRequest]");

        final String token = getJwtFromRequest(request);

        makeAuthentication(request, token).ifPresent(auth -> {
            SecurityContextHolder.getContext().setAuthentication(auth);
        });
    }

    private Boolean isRequestAvailableToGuest(HttpServletRequest request) {
        return request.getRequestURI().contains("/realEstate") && request.getMethod().equals("GET");
    }

    private Optional<UserAuthentication> makeAuthentication(HttpServletRequest request, String token) {

        log.info("[AuthenticationUtil.makeAuthentication]");

        UserAuthentication authentication = null;

        if(isTokenValid(token)) {
            String role = jwtProvider.getJwtUserDetails(token).getRole().toString();
            log.info("[AuthenticationUtil.makeAuthentication : {} 권한 부여]", role);
            authentication =  UserAuthentication.from(jwtProvider.getJwtUserDetails(token));
        } else if (isRequestAvailableToGuest(request)) {
            log.info("[AuthenticationUtil.makeAuthentication : Guest 권한 부여]");
            authentication = UserAuthentication.makeGuestAuthentication();
        }

        if(authentication != null) {
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        }

        return Optional.ofNullable(authentication);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        } else {
            return null;
        }

    }

    private boolean isTokenValid(String token) {
        return token != null && !jwtProvider.isExpiredToken(token);
    }

}
