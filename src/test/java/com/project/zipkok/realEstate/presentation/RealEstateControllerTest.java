package com.project.zipkok.realEstate.presentation;

import com.project.zipkok.controller.RealEstateController;
import com.project.zipkok.dto.GetRealEstateResponse;
import com.project.zipkok.global.CommonControllerTest;
import com.project.zipkok.service.RealEstateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.project.zipkok.realEstate.fixture.RealEstateFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RealEstateController.class)
public class RealEstateControllerTest extends CommonControllerTest {

    @MockBean
    private RealEstateService realEstateService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("매물_상세정보_테스트_매물존재")
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
}



