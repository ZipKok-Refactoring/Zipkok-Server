package com.project.zipkok.kok.controller;

import com.google.gson.Gson;
import com.project.zipkok.config.SecurityConfig;
import com.project.zipkok.controller.KokController;
import com.project.zipkok.dto.*;
import com.project.zipkok.service.KokService;
import com.project.zipkok.util.jwt.JwtUserDetails;
import com.project.zipkok.util.security.AuthenticationUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static com.project.zipkok.kok.controller.KokControllerResponseMatcher.*;
import static com.project.zipkok.kok.response.MakeTestKokControllerResponse.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
            controllers = KokController.class,
            excludeAutoConfiguration = SecurityAutoConfiguration.class,
            excludeFilters = {
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
)
@DisplayName("KokController 테스트")
public class KokControllerTest {

    @MockBean
    private KokService kokService;
    @MockBean
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private MockMvc mockMvc;

    Gson gson = new Gson();


    @DisplayName("콕 무한페이징 api 성공")
    @Test
    void 콕_리스트_반환() throws Exception {
        //given
        GetKokResponse response = makeTestGetKokResponse(
                1L,
                1L,
                "test.com",
                "testAddress",
                "testDetailAddress",
                "testAgent",
                "testMonthly",
                "testOneRoom",
                1000L,
                10L,
                true
        );

        given(kokService.getKoks(any(JwtUserDetails.class), any(Pageable.class))).willReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/kok?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(expectedGetKokResponse());
    }

    @DisplayName("콕 세부페이지 로드 api 성공")
    @Test
    void 콕_세부페이지_반환() throws Exception {
        //given
        GetKokDetailResponse response = makeTestGetkokDetailResponse(
                1L,
                1,
                List.of("test1","test2"),
                1L,
                "testAddress",
                "testDetailAddress",
                "testMonthly",
                1000L,
                10L,
                "testDetail",
                10f,
                1,
                "testOneRoom",
                1,
                5,
                1.1,
                1.1
        );

        given(kokService.getKokDetail(any(JwtUserDetails.class), any(Long.class))).willReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/kok/1/detail")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(expectedGetKokDetailResponse());
    }

    @DisplayName("콕 집주변 정보 로드 api 성공")
    @Test
    void 콕_집주변_정보_반환() throws Exception {
        //given
        GetKokOuterInfoResponse response = makeTestGetKokOuterInfoResponse(
                Arrays.asList("test1", "test2", "test3", "test4"),
                List.of("test1" ,"test2"),
                List.of(1,2),
                List.of("1","2","3")
        );

        given(kokService.getKokOuterInfo(any(JwtUserDetails.class), any(Long.class))).willReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/kok/1/outer")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(expectedGetKokOuterInfoResponse());
    }

    @DisplayName("콕 집내부 정보 로드 api 성공")
    @Test
    void 콕_집내부_정보_반환() throws Exception {
        //given
        GetKokInnerInfoResponse response = makeTestGetKokInnerInfoResponse(
                Arrays.asList("test1", "test2", "test3", "test4"),
                List.of("test1" ,"test2"),
                List.of(1,2),
                List.of("1","2","3"),
                "남쪽"
        );

        given(kokService.getKokInnerInfo(any(JwtUserDetails.class), any(Long.class))).willReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/kok/1/inner")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(expectedGetKokInnerInfoResponse());
    }

    @DisplayName("콕 집 중개/계약 정보 로드 api 성공")
    @Test
    void 콕_집_중개_계약_정보_반환() throws Exception {
        //given
        GetKokContractResponse response = makeTestGetKokContractResponse(
                List.of("test1" ,"test2"),
                List.of(1,2),
                List.of("1","2","3"),
                2,
                Arrays.asList("test1", "test2", "test3")
        );

        given(kokService.getKokContractInfo(any(JwtUserDetails.class), any(Long.class))).willReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/kok/1/contract")
                    .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(expectedGetKokContractResponse());
    }

    @DisplayName("콕 후기 정보 로드 api 성공")
    @Test
    void 콕_후기_정보_반환() throws Exception {
        //given
        GetKokReviewInfoResponse response = makeTestGetKokReviewInfoResponse(
                Arrays.asList("1", "2", "3"),
                5,5,5,5,
                "테스트입니다"
        );

        given(kokService.getKokReviewInfo(any(JwtUserDetails.class), any(Long.class))).willReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/kok/1/review")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(expectedGetKokReviewInfoResponse());
    }

    @DisplayName("콕 설정 정보 로드 api 성공")
    @Test
    void 콕_설정_정보_반환() throws Exception {
        //given
        GetKokConfigInfoResponse response = makeTestGetKokConfigInfoResponse(
                Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"),
                1L, "test", 1,
                5,5,5,5, "테스트입니다",
                "남쪽"
        );

        given(kokService.getKokConfigInfo(any(JwtUserDetails.class), any(Long.class))).willReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/kok/config?kokId=1")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(expectedGetKokConfigInfoResponse());
    }

    @DisplayName("새 콕 생성 api 성공")
    @Test
    void 새_콕_생성() throws Exception {
        //given
        PostOrPutKokResponse response = makeTestPostOrPutKokResponse(1L);
        MockMultipartFile request = makeTestPostOrPutKokRequest("file", "test.txt", "text/plain", "test");

        given(kokService.createOrUpdateKok(any(JwtUserDetails.class), any(), any())).willReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.multipart(HttpMethod.POST, "/kok")
                    .file(request)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
        );

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(expectedPostKokResponse());
    }

    @DisplayName("콕 수정 api 성공")
    @Test
    void 콕_수정() throws Exception {
        //given
        MockMultipartFile request = makeTestPostOrPutKokRequest("file", "test.txt", "text/plain", "test");

        given(kokService.createOrUpdateKok(any(JwtUserDetails.class), any(), any())).willReturn(null);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/kok")
                    .file(request)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
        );

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(expectedPutKokResponse());
    }
}
