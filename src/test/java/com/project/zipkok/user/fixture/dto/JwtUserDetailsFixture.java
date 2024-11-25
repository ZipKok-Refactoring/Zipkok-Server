package com.project.zipkok.user.fixture.dto;

import com.project.zipkok.common.enums.Role;
import com.project.zipkok.util.jwt.JwtUserDetails;

public class JwtUserDetailsFixture {
    public static JwtUserDetails createDefaultJwtUserDetails() {
        return JwtUserDetails.builder()
                .role(Role.USER)
                .userId(1L)
                .email("test@test.com")
                .build();
    }
}
