package com.project.zipkok.kok.controller;

import org.hamcrest.Matchers;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class KokControllerResponseMatcher {
    public static ResultMatcher expectedGetKokResponse() {
        return result -> {
            MockMvcResultMatchers.jsonPath("$.code").value("7001").match(result);
            MockMvcResultMatchers.jsonPath("$.message").value("콕리스트 조회 성공").match(result);
            MockMvcResultMatchers.jsonPath("$.result.koks").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.koks", Matchers.hasSize(2)).match(result);
        };
    }

    public static ResultMatcher expectedGetKokDetailResponse() {
        return result -> {
            MockMvcResultMatchers.jsonPath("$.code").value("7002").match(result);
            MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo("콕 세부조회 성공")).match(result);
            MockMvcResultMatchers.jsonPath("$.result.kokId").exists().match(result);
            MockMvcResultMatchers.jsonPath("$.result.imageInfo").exists().match(result);
            MockMvcResultMatchers.jsonPath("$.result.imageInfo.imageNumber", Matchers.equalTo(2)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.imageInfo.imageUrls").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.imageInfo.imageUrls[0]", Matchers.equalTo("https//test.com")).match(result);
            MockMvcResultMatchers.jsonPath("$.result.imageInfo.imageUrls[1]", Matchers.equalTo("https//test2.com")).match(result);
            MockMvcResultMatchers.jsonPath("$.result.realEstateId").exists().match(result);
            MockMvcResultMatchers.jsonPath("$.result.address", Matchers.equalTo("test/test")).match(result);
            MockMvcResultMatchers.jsonPath("$.result.detailAddress", Matchers.equalTo("101")).match(result);
            MockMvcResultMatchers.jsonPath("$.result.transactionType", Matchers.equalTo("월세")).match(result);
            MockMvcResultMatchers.jsonPath("$.result.deposit", Matchers.equalTo(100000)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.price", Matchers.equalTo(500)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.detail", Matchers.equalTo("테스트입니다.")).match(result);
            MockMvcResultMatchers.jsonPath("$.result.areaSize", Matchers.equalTo(1.1)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.pyeongsu", Matchers.equalTo(5)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.realEstateType", Matchers.equalTo("원룸")).match(result);
            MockMvcResultMatchers.jsonPath("$.result.floorNum", Matchers.equalTo(2)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.administrativeFee", Matchers.equalTo(5000)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.latitude", Matchers.equalTo(127.1)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.longitude", Matchers.equalTo(127.1)).match(result);
        };
    }

    public static ResultMatcher expectedGetKokOuterInfoResponse() {
        return result -> {
            MockMvcResultMatchers.jsonPath("$.code").value("7003").match(result);
            MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo("콕의 집 주변 정보 조회 성공")).match(result);
            MockMvcResultMatchers.jsonPath("$.result.highlights").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.highlights", Matchers.hasSize(4)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.options", Matchers.hasSize(2)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[0].option", Matchers.equalTo("test1")).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[0].orderNumber", Matchers.equalTo(1)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[0].detailOptions").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[0].detailOptions", Matchers.hasSize(3)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[1].option", Matchers.equalTo("test2")).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[1].orderNumber", Matchers.equalTo(2)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[1].detailOptions").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[1].detailOptions", Matchers.hasSize(3)).match(result);
        };
    }

    public static ResultMatcher expectedGetKokInnerInfoResponse() {
        return result -> {
            MockMvcResultMatchers.jsonPath("$.code").value("7004").match(result);
            MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo("콕의 집 내부 정보 조회 성공")).match(result);
            MockMvcResultMatchers.jsonPath("$.result.furnitureOptions").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.furnitureOptions", Matchers.hasSize(4)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.options", Matchers.hasSize(2)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[0].option", Matchers.equalTo("test1")).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[0].orderNumber", Matchers.equalTo(1)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[0].detailOptions").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[0].detailOptions", Matchers.hasSize(3)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[1].option", Matchers.equalTo("test2")).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[1].orderNumber", Matchers.equalTo(2)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[1].detailOptions").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[1].detailOptions", Matchers.hasSize(3)).match(result);
        };
    }

    public static ResultMatcher expectedGetKokContractResponse(){
        return result -> {
            MockMvcResultMatchers.jsonPath("$.code").value("7005").match(result);
            MockMvcResultMatchers.jsonPath("$.message").value("콕의 중개 계약 정보 조회 성공").match(result);
            MockMvcResultMatchers.jsonPath("$.result").exists().match(result);
            MockMvcResultMatchers.jsonPath("$.result.options").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.options", Matchers.hasSize(2)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[0].option", Matchers.equalTo("test1")).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[0].orderNumber", Matchers.equalTo(1)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[0].detailOptions", Matchers.hasSize(3)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[1].option", Matchers.equalTo("test2")).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[1].orderNumber", Matchers.equalTo(2)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.options[1].detailOptions", Matchers.hasSize(3)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.imageInfo.imageNumber", Matchers.equalTo(2)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.imageInfo.imageUrls", Matchers.hasSize(3)).match(result);
        };
    }

    public static ResultMatcher expectedGetKokReviewInfoResponse(){
        return result -> {
            MockMvcResultMatchers.jsonPath("$.code").value("7006").match(result);
            MockMvcResultMatchers.jsonPath("$.message").value("콕의 리뷰 정보 조회 성공").match(result);
            MockMvcResultMatchers.jsonPath("$.result").exists().match(result);
            MockMvcResultMatchers.jsonPath("$.result.impressions").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.impressions", Matchers.hasSize(3)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.facilityStarCount", Matchers.equalTo(5)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.infraStarCount", Matchers.equalTo(5)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.structureStarCount", Matchers.equalTo(5)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.vibeStarCount", Matchers.equalTo(5)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.reviewText", Matchers.equalTo("테스트입니다")).match(result);
        };
    }

    public static ResultMatcher expectedGetKokConfigInfoResponse(){
        return result -> {
            MockMvcResultMatchers.jsonPath("$.code").value("7009").match(result);
            MockMvcResultMatchers.jsonPath("$.message").value("회원 설정 정보 조회 성공").match(result);
            MockMvcResultMatchers.jsonPath("$.result").exists().match(result);
            MockMvcResultMatchers.jsonPath("$.result.highlights").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.highlights", Matchers.hasSize(9)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.checkedHighlights").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.checkedHighlights", Matchers.hasSize(9)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.furnitureOptions").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.furnitureOptions", Matchers.hasSize(9)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.checkedFurnitureOptions").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.checkedFurnitureOptions", Matchers.hasSize(9)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.outerImageUrls").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.outerImageUrls", Matchers.hasSize(9)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.innerImageUrls").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.innerImageUrls", Matchers.hasSize(9)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.contractImageUrls").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.contractImageUrls", Matchers.hasSize(9)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.direction", Matchers.equalTo("남쪽")).match(result);

            MockMvcResultMatchers.jsonPath("$.result.reviewInfo.impressions").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.reviewInfo.impressions", Matchers.hasSize(9)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.reviewInfo.checkedImpressions").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.reviewInfo.checkedImpressions", Matchers.hasSize(9)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.reviewInfo.facilityStarCount", Matchers.equalTo(5)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.reviewInfo.infraStarCount", Matchers.equalTo(5)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.reviewInfo.structureStarCount", Matchers.equalTo(5)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.reviewInfo.vibeStarCount", Matchers.equalTo(5)).match(result);
            MockMvcResultMatchers.jsonPath("$.result.reviewInfo.reviewText", Matchers.equalTo("테스트입니다")).match(result);

            MockMvcResultMatchers.jsonPath("$.result.outerOptions").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.innerOptions").isArray().match(result);
            MockMvcResultMatchers.jsonPath("$.result.contractOptions").isArray().match(result);
        };
    }

    public static ResultMatcher expectedPostKokResponse(){
        return result -> {
            MockMvcResultMatchers.jsonPath("$.code").value("7011").match(result);
            MockMvcResultMatchers.jsonPath("$.message").value("콕 등록 성공").match(result);
            MockMvcResultMatchers.jsonPath("$.result").exists().match(result);
            MockMvcResultMatchers.jsonPath("$.result.kokId", Matchers.equalTo(1)).match(result);
        };
    }

    public static ResultMatcher expectedPutKokResponse(){
        return result -> {
            MockMvcResultMatchers.jsonPath("$.code").value("7014").match(result);
            MockMvcResultMatchers.jsonPath("$.message").value("콕 수정 성공").match(result);
        };
    }

}
