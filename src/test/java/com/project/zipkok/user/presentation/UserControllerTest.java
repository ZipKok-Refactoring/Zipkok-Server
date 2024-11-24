package com.project.zipkok.user.presentation;

import com.google.gson.Gson;
import com.project.zipkok.common.enums.Gender;
import com.project.zipkok.common.enums.OAuthProvider;
import com.project.zipkok.controller.UserController;
import com.project.zipkok.dto.PostSignUpRequest;
import com.project.zipkok.service.UserService;
import com.project.zipkok.util.jwt.AuthTokens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void signUpSuccess() throws Exception {
        //given
        PostSignUpRequest signUpRequest = signUpRequest();
        AuthTokens signUpResponse = signUpResponse();

        doReturn(signUpResponse).when(userService)
                .signUp(any(PostSignUpRequest.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(signUpRequest))
        );

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.accessToken", signUpResponse.getAccessToken()).exists())
                .andExpect(jsonPath("$.result.refreshToken", signUpResponse.getAccessToken()).exists())
                .andExpect(jsonPath("$.result.expiresIn", signUpResponse.getExpiresIn()).exists())
                .andExpect(jsonPath("$.result.refreshTokenExpiresIn", signUpResponse.getRefreshTokenExpiresIn()).exists())
                .andReturn();
    }

    private PostSignUpRequest signUpRequest() {
        return PostSignUpRequest.builder()
                .nickname("test")
                .oauthProvider(OAuthProvider.KAKAO.toString())
                .email("test@test.com")
                .gender(Gender.MALE.toString())
                .birthday("001212")
                .build();
    }

    private AuthTokens signUpResponse() {
        return AuthTokens.builder()
                .accessToken("test access token")
                .refreshToken("test refresh token")
                .expiresIn(10000000L)
                .refreshTokenExpiresIn(10000000L)
                .build();
    }

    @Test
    void onBoarding() {
    }

    @Test
    void myPage() {
    }

    @Test
    void myPageDetail() {
    }

    @Test
    void updateMyInfo() {
    }

    @Test
    void loadKokOption() {
    }

    @Test
    void updateKokOption() {
    }

    @Test
    void logout() {
    }

    @Test
    void deregistration() {
    }

    @Test
    void updateFilter() {
    }
}