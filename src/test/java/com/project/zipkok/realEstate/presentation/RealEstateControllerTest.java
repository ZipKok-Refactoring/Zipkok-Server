package com.project.zipkok.realEstate.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.zipkok.common.enums.RealEstateType;
import com.project.zipkok.common.enums.TransactionType;
import com.project.zipkok.common.exception.RealEstateException;
import com.project.zipkok.controller.RealEstateController;
import com.project.zipkok.dto.*;
import com.project.zipkok.global.CommonControllerTest;
import com.project.zipkok.service.RealEstateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.util.List;

import static com.project.zipkok.common.response.status.BaseExceptionResponseStatus.*;
import static com.project.zipkok.realEstate.fixture.RealEstateFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RealEstateController.class)
public class RealEstateControllerTest extends CommonControllerTest {

    @MockBean
    private RealEstateService realEstateService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("매물_상세정보_테스트_매물_존재")
    void getRealEstateTest() throws Exception {
        //given
        List<GetRealEstateResponse.RealEstateBriefInfo> realEstateBriefInfoList = List.of(
                GetRealEstateResponse.RealEstateBriefInfo.from(MONTHLY_ONEROOM_02),
                GetRealEstateResponse.RealEstateBriefInfo.from(MONTHLY_APARTMENT_01),
                GetRealEstateResponse.RealEstateBriefInfo.from(YEARLY_ONEROOM_01));


        GetRealEstateResponse getRealEstateResponse = GetRealEstateResponse.of(MONTHLY_ONEROOM_01, false, false, null, realEstateBriefInfoList);

        given(realEstateService.getRealEstateInfo(any(), anyLong())).willReturn(getRealEstateResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get("/realEstate/{realEstateId}", MONTHLY_ONEROOM_01.getRealEstateId()))
                .andDo(print());
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.realEstateId").value(MONTHLY_ONEROOM_01.getRealEstateId()));

    }

    @Test
    @DisplayName("매물_상세정보_테스트_매물_없음")
    void getRealEstateTest2() throws Exception {
        //given
        given(realEstateService.getRealEstateInfo(any(), anyLong())).willThrow(new RealEstateException(INVALID_PROPERTY_ID));
        //when
        ResultActions resultActions = mockMvc.perform(get("/realEstate/{realEstateId}", 0L))
                .andDo(print());
        //then
        resultActions
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(INVALID_PROPERTY_ID.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_PROPERTY_ID.getMessage()));
    }

    @Test
    @DisplayName("매물_직접_등록_성공")
    void registerRealEstate() throws Exception {
        //given
        PostRealEstateRequest postRealEstateRequest = PostRealEstateRequest.builder()
                .realEstateName("자취방1")
                .transactionType(TransactionType.MONTHLY.name())
                .realEstateType(RealEstateType.ONEROOM.name())
                .deposit(10000000L)
                .price(400000L)
                .administrativeFee(10000)
                .address("서울특별시 광진구 자양로 23나길 24")
                .detailAddress("202호")
                .latitude(37.123456)
                .longitude(127.123456)
                .pyeongsu(12)
                .floorNum(2)
                .build();

        given(realEstateService.registerRealEstate(any(), any())).willReturn(new PostRealEstateResponse(10L));

        //when
        ResultActions resultActions = mockMvc.perform(post("/realEstate")
                .content(objectMapper.writeValueAsString(postRealEstateRequest))
                .contentType("application/json"))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.realEstateId").value(10L));
    }

    @Test
    @DisplayName("매물_직접_등록_요청_검증_오류")
    void registerRealEstate2() throws Exception {
        //given
        //거래유형, 매물유형, 관리비 정보가 없거나 잘못된 요청 객체
        PostRealEstateRequest postRealEstateRequest = PostRealEstateRequest.builder()
                .realEstateName("매물이름")
                .transactionType("DAILY")
                .realEstateType("ZEROROOM")
                .price(400000L)
                .address("서울특별시 광진구 자양로 23나길 24")
                .detailAddress("202호")
                .latitude(37.123456)
                .longitude(127.123456)
                .pyeongsu(12)
                .floorNum(2)
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(post("/realEstate")
                        .content(objectMapper.writeValueAsString(postRealEstateRequest))
                        .contentType("application/json"))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors[*].code")
                        .value(org.hamcrest.Matchers.hasItems(
                                INVALID_ESTATE_TYPE_FORMAT.getCode(),
                                INVALID_MANAGEMENT_FEE_FORMAT.getCode(),
                                INVALID_MANAGEMENT_FEE_FORMAT.getCode())));

    }

    @Test
    @DisplayName("지도에서_매물_조회_성공")
    void realEstateOnMapTest() throws Exception {
        //given
        GetRealEstateOnMapRequest request = GetRealEstateOnMapRequest.builder()
                .southWestLat(0.0)
                .southWestLon(0.0)
                .northEastLat(200.0)
                .northEastLon(200.0)
                .transactionType(TransactionType.MONTHLY)
                .realEstateType(RealEstateType.ONEROOM)
                .depositMin(0L)
                .depositMax(1000000000L)
                .priceMin(0L)
                .priceMax(100000000L)
                .build();

        GetTempRealEstateResponse response = GetTempRealEstateResponse.builder().build();
        given(realEstateService.getRealEstate(any(), any())).willReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(get("/realEstate")
                .params(convertToQueryParams(request)))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists());

    }

    @Test
    @DisplayName("지도에서_매물_조회_요청_검증_오류")
    void realEstateOnMapTest2() throws Exception {
        //given
        //남서쪽 위도, 경도가 북동쪽 위도, 경도보다 큰 경우
        GetRealEstateOnMapRequest request = GetRealEstateOnMapRequest.builder()
                .southWestLat(10.0)
                .southWestLon(10.0)
                .northEastLat(5.0)
                .northEastLon(200.0)
                .transactionType(TransactionType.MONTHLY)
                .realEstateType(RealEstateType.ONEROOM)
                .depositMin(0L)
                .depositMax(1000000000L)
                .priceMin(0L)
                .priceMax(100000000L)
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(get("/realEstate")
                        .params(convertToQueryParams(request)))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors[*].code")
                        .value(org.hamcrest.Matchers.hasItems(MIN_POINT_IS_BIGGER_THAN_MAX_POINT.getCode())));

    }

    public static <T> MultiValueMap<String, String> convertToQueryParams(T request) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Field[] fields = request.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(request);
                if (value != null) {
                    params.add(field.getName(), value.toString());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("필드 접근 오류: " + field.getName(), e);
            }
        }

        return params;
    }
}



