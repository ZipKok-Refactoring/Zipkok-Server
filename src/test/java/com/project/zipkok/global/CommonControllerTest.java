package com.project.zipkok.global;

import com.project.zipkok.common.enums.Role;
import com.project.zipkok.global.config.TestSecurityConfig;
import com.project.zipkok.model.User;
import com.project.zipkok.util.jwt.JwtProvider;
import com.project.zipkok.util.jwt.JwtUserDetails;
import com.project.zipkok.util.security.AuthenticationUtil;
import com.project.zipkok.util.security.UserAuthentication;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.context.SecurityContextHolder;

@Import(TestSecurityConfig.class)
public class CommonControllerTest {

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private AuthenticationUtil authenticationUtil;

    @BeforeEach
    public void setUp() {
        UserAuthentication userAuthentication =
                new UserAuthentication(JwtUserDetails.from(User.builder().userId(1L).build()), null, Role.USER.getAuthority());
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
    }
}
