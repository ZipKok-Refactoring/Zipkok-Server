package com.project.zipkok.controller;

import com.project.zipkok.common.exception.RealEstateException;
import com.project.zipkok.common.response.BaseResponse;
import com.project.zipkok.dto.*;
import com.project.zipkok.service.RealEstateService;
import com.project.zipkok.util.jwt.JwtUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.project.zipkok.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@RestController
@RequestMapping("/realEstate")
@RequiredArgsConstructor
@Tag(name = "RealEstate API", description = "매물 관련 API")
public class RealEstateController {

    private final RealEstateService realEstateService;

    @Operation(summary = "매물 상세정보 API", description = "매물의 상세정보를 응답하는 API입니다.")
    @GetMapping("/{realEstateId}")
    public BaseResponse<GetRealEstateResponse> getRealEstate(@Parameter(hidden = true) @AuthenticationPrincipal JwtUserDetails jwtUserDetail, @Parameter(name = "realEstateId", description = "매물의 Id", in = ParameterIn.PATH)
                                                                 @PathVariable(value = "realEstateId") Long realEstateId) {
        log.info("[RealEstateController.getRealEstate]");

        if (realEstateId == null) {
            throw new RealEstateException(INVALID_PROPERTY_ID);
        }

        return new BaseResponse<>(PROPERTY_DETAIL_QUERY_SUCCESS, realEstateService.getRealEstateInfo(jwtUserDetail, realEstateId));
    }

    @Operation(summary = "매물 직접등록 API", description = "매물을 직접 등록할 때 사용하는 API입니다.")
    @PostMapping("")
    public BaseResponse<PostRealEstateResponse> registerRealEstate(@Parameter(hidden = true) @AuthenticationPrincipal JwtUserDetails jwtUserDetail,
                                                                   @Validated @RequestBody PostRealEstateRequest postRealEstateRequest) {

        log.info("[RealEstateController.registerReslEstate]");

        return new BaseResponse<>(PROPERTY_REGISTRATION_SUCCESS, realEstateService.registerRealEstate(jwtUserDetail, postRealEstateRequest));
    }

    @Operation(summary = "홈 화면 지도 API", description = "지도 상에 띄울 매물을 받기 위한 api입니다.")
    @GetMapping("")
    public BaseResponse<GetMapRealEstateResponse> realEstateOnMap(@Parameter(hidden=true) @AuthenticationPrincipal JwtUserDetails jwtUserDetail,
                                                                       @Validated @ModelAttribute GetRealEstateOnMapRequest getRealEstateOnMapRequest){
        log.info("{UserController.realEstateOnMap}");

        return new BaseResponse<>(PROPERTY_MAP_QUERY_SUCCESS, this.realEstateService.getRealEstate(jwtUserDetail, getRealEstateOnMapRequest));
    }
}
