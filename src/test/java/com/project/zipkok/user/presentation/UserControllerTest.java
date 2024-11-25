package com.project.zipkok.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.zipkok.common.enums.Gender;
import com.project.zipkok.common.enums.OAuthProvider;
import com.project.zipkok.common.response.BaseResponse;
import com.project.zipkok.controller.UserController;
import com.project.zipkok.dto.*;
import com.project.zipkok.global.CommonControllerTest;
import com.project.zipkok.model.Option;
import com.project.zipkok.service.UserService;
import com.project.zipkok.user.fixture.dto.JwtUserDetailsFixture;
import com.project.zipkok.user.fixture.dto.UserDtoFixture;
import com.project.zipkok.util.jwt.AuthTokens;
import com.project.zipkok.util.jwt.JwtUserDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.project.zipkok.common.response.status.BaseExceptionResponseStatus.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest extends CommonControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입_요청에_성공한다")
    void signUpSuccess() throws Exception {
        //given
        PostSignUpRequest signUpRequest = signUpRequest();
        AuthTokens signUpResponse = signUpResponse();

        doReturn(signUpResponse).when(userService)
                .signUp(any(PostSignUpRequest.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content(objectMapper.writeValueAsString(signUpRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult mvcResult = resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.accessToken", signUpResponse.getAccessToken()).exists())
                .andExpect(jsonPath("$.result.refreshToken", signUpResponse.getAccessToken()).exists())
                .andExpect(jsonPath("$.result.expiresIn", signUpResponse.getExpiresIn()).exists())
                .andExpect(jsonPath("$.result.refreshTokenExpiresIn", signUpResponse.getRefreshTokenExpiresIn()).exists())
                .andReturn();
    }

    @Test
    @DisplayName("회원가입_요청시_이메일_형식이_잘못되면_요청에_실패한다")
    void signUpFail() throws Exception {

        //given
        PostSignUpRequest signUpRequest = UserDtoFixture.createCustomPostSignUpRequest("test", "invalid test email", "19970609");
        AuthTokens signUpResponse = signUpResponse();

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content(objectMapper.writeValueAsString(signUpRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors[*].code")
                        .value(org.hamcrest.Matchers.hasItems(
                                INVALID_FIELD_FORMAT.getCode())));
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
    @DisplayName("회원가입_성공_후_온보딩_정보_업데이트_성공")
    void onBoarding() throws Exception {

        // given
        JwtUserDetails jwtUserDetails = JwtUserDetailsFixture.createDefaultJwtUserDetails();
        PatchOnBoardingRequest requestDto = UserDtoFixture.createDefaultPatchOnBoardingRequest();
        BaseResponse<Object> expectResponse = new BaseResponse<>(MEMBER_INFO_UPDATE_SUCCESS, null);

        doReturn(null).when(userService)
                .setOnBoarding(any(PatchOnBoardingRequest.class), any(JwtUserDetails.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch("/user")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(expectResponse.getCode()))
                .andExpect(jsonPath("$.message").value(expectResponse.getMessage()));

    }

    @Test
    @DisplayName("마이페이지_회원정보_조회_성공")
    void myPage() throws Exception {

        // given
        JwtUserDetails jwtUserDetails = JwtUserDetailsFixture.createDefaultJwtUserDetails();
        GetMyPageResponse response = UserDtoFixture.createDefaultGetMyPageResponse();
        BaseResponse<GetMyPageResponse> expectResponse = new BaseResponse<>(MY_PAGE_INFO_LOAD_SUCCESS, response);

        doReturn(response).when(userService)
                .myPageLoad(any(JwtUserDetails.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/user")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(expectResponse.getCode()))
                .andExpect(jsonPath("$.message").value(expectResponse.getMessage()))
                .andExpect(jsonPath("$.result").exists());

    }

    @Test
    @DisplayName("마이페이지_회원정보_상세조회_성공")
    void myPageDetail() throws Exception {

        // given
        JwtUserDetails jwtUserDetails = JwtUserDetailsFixture.createDefaultJwtUserDetails();
        GetMyPageDetailResponse response = UserDtoFixture.createDefaultGetMyPageDetailResponse();
        BaseResponse<GetMyPageDetailResponse> expectResponse = new BaseResponse<>(MY_PAGE_INFO_LOAD_SUCCESS, response);

        doReturn(response).when(userService)
                .myPageDetailLoad(any(JwtUserDetails.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/user/detail")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(expectResponse.getCode()))
                .andExpect(jsonPath("$.message").value(expectResponse.getMessage()))
                .andExpect(jsonPath("$.result").exists());
    }

    @Test
    @DisplayName("프로필_수정_요청_성공")
    void updateMyInfo() throws Exception {

        // given
        JwtUserDetails jwtUserDetails = JwtUserDetailsFixture.createDefaultJwtUserDetails();
        BaseResponse<Object> expectResponse = new BaseResponse<>(MEMBER_INFO_UPDATE_SUCCESS, null);

        doReturn(expectResponse).when(userService)
                .updateMyInfo(any(JwtUserDetails.class), any(), any());

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/user")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(expectResponse.getCode()))
                .andExpect(jsonPath("$.message").value(expectResponse.getMessage()));
    }

    @Test
    @DisplayName("마이페이지_리스트_수정_정보_로드_성공")
    void loadKokOption() throws Exception {

        // given
        JwtUserDetails jwtUserDetails = JwtUserDetailsFixture.createDefaultJwtUserDetails();
        List<String> highlights = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        GetKokOptionLoadResponse getKokOptionLoadResponse = UserDtoFixture.createDefaultGetKokOptionLoadResponse(highlights, options, options, options);
        BaseResponse<GetKokOptionLoadResponse> expectResponse = new BaseResponse<>(MEMBER_LIST_ITEM_QUERY_SUCCESS, getKokOptionLoadResponse);

        doReturn(getKokOptionLoadResponse).when(userService)
                .loadKokOption(any(JwtUserDetails.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/user/kokOption")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(expectResponse.getCode()))
                .andExpect(jsonPath("$.message").value(expectResponse.getMessage()))
                .andExpect(jsonPath("$.result").exists());
    }

    @Test
    @DisplayName("회원의_콕리스트_항목_설정_정보_수정_성공")
    void updateKokOption() throws Exception {

        // given
        JwtUserDetails jwtUserDetails = JwtUserDetailsFixture.createDefaultJwtUserDetails();
        List<String> highlights = new ArrayList<>();
        List<PostUpdateKokOptionRequest.Option> options = new ArrayList<>();
        PostUpdateKokOptionRequest request = UserDtoFixture.createDefaultPostUpdateKokOptionRequest(highlights, options, options, options);
        BaseResponse<Object> expectResponse = new BaseResponse<>(MEMBER_LIST_ITEM_UPDATE_SUCCESS, null);

        doReturn(request).when(userService)
                .updateKokOption(any(JwtUserDetails.class), any(PostUpdateKokOptionRequest.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/user/kokOption")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(expectResponse.getCode()))
                .andExpect(jsonPath("$.message").value(expectResponse.getMessage()))
                .andExpect(jsonPath("$.result").exists());
    }
}