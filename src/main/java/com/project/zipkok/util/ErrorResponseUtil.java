package com.project.zipkok.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.zipkok.common.response.BaseExceptionResponse;
import com.project.zipkok.common.response.status.ResponseStatus;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ErrorResponseUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void setResponse(HttpServletResponse response, ResponseStatus responseStatus) throws IOException {

        BaseExceptionResponse errorResponse = new BaseExceptionResponse(responseStatus);

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}
