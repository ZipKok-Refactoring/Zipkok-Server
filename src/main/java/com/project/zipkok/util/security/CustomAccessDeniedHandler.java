package com.project.zipkok.util.security;

import com.project.zipkok.util.ErrorResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.project.zipkok.common.response.status.BaseExceptionResponseStatus.INVALID_TOKEN;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("JwtAuthenticationEntryPoint : enter CustomAccessDeniedHandler ");

        ErrorResponseUtil.setResponse(response, INVALID_TOKEN);
    }
}
