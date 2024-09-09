package com.project.zipkok.kok.controller;

import com.project.zipkok.common.enums.Role;
import com.project.zipkok.dto.*;
import com.project.zipkok.util.jwt.JwtUserDetails;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class MakeTestResponse {

    private JwtUserDetails makeTestJwtUserDetails(){
        return JwtUserDetails.builder()
                .userId(1L)
                .role(Role.USER)
                .build();
    }

    public static GetKokResponse makeTestGetKokResponse(){
        List<GetKokResponse.Koks> koks = new ArrayList<>();

        koks.add(GetKokResponse.Koks.builder()
                .kokId(1L)
                .realEstateId(1L)
                .imageUrl("https//test.com")
                .address("test/test")
                .detailAddress("101")
                .estateAgent("test부동산")
                .transactionType("월세")
                .realEstateType("원룸")
                .deposit(5000000L)
                .price(500000L)
                .isZimmed(true)
                .build()
        );
        koks.add(GetKokResponse.Koks.builder()
                .kokId(2L)
                .realEstateId(2L)
                .imageUrl("https//test2.com")
                .address("test2/test2")
                .detailAddress("102")
                .estateAgent("test2부동산")
                .transactionType("전세")
                .realEstateType("투룸")
                .deposit(10000000L)
                .isZimmed(false)
                .build()
        );

        return GetKokResponse.builder()
                .koks(koks)
                .build();
    }

    public static GetKokDetailResponse makeTestGetkokDetailResponse(){
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("https//test.com");
        imageUrls.add("https//test2.com");

        return GetKokDetailResponse.builder()
                .kokId(1L)
                .imageInfo(
                        GetKokDetailResponse.ImageInfo.builder()
                                .imageNumber(2)
                                .imageUrls(imageUrls)
                                .build()
                )
                .realEstateId(1L)
                .address("test/test")
                .detailAddress("101")
                .transactionType("월세")
                .deposit(100000L)
                .price(500L)
                .detail("테스트입니다.")
                .areaSize(1.1f)
                .pyeongsu(5)
                .realEstateType("원룸")
                .floorNum(2)
                .administrativeFee(5000)
                .latitude(127.1)
                .longitude(127.1)
                .build();
    }

    public static GetKokOuterInfoResponse makeTestGetKokOuterInfoResponse(){
        List<String> highlights = Arrays.asList("test1", "test2", "test3", "test4");
        List<GetKokOuterInfoResponse.OuterOption> outerOptions = Arrays.asList(
                GetKokOuterInfoResponse.OuterOption.builder()
                        .option("test1")
                        .orderNumber(1)
                        .detailOptions(Arrays.asList("1", "2", "3"))
                        .build(),
                GetKokOuterInfoResponse.OuterOption.builder()
                        .option("test2")
                        .orderNumber(2)
                        .detailOptions(Arrays.asList("1", "2", "3"))
                        .build()
        );

        return GetKokOuterInfoResponse.builder()
                .highlights(highlights)
                .options(outerOptions)
                .build();
    }

    public static GetKokInnerInfoResponse makeTestGetKokInnerInfoResponse(){
        List<String> furnitures = Arrays.asList("test1", "test2", "test3", "test4");
        List<GetKokInnerInfoResponse.InnerOption> innerOptions = Arrays.asList(
                GetKokInnerInfoResponse.InnerOption.builder()
                        .option("test1")
                        .orderNumber(1)
                        .detailOptions(Arrays.asList("1", "2", "3"))
                        .build(),
                GetKokInnerInfoResponse.InnerOption.builder()
                        .option("test2")
                        .orderNumber(2)
                        .detailOptions(Arrays.asList("1", "2", "3"))
                        .build()
        );

        return GetKokInnerInfoResponse.builder()
                .furnitureOptions(furnitures)
                .direction("남쪽")
                .options(innerOptions)
                .build();
    }

    public static GetKokContractResponse makeTestGetKokContractResponse() {
        List<GetKokContractResponse.ContractOptions> contractOptions = Arrays.asList(
                GetKokContractResponse.ContractOptions.builder()
                        .option("test1")
                        .orderNumber(1)
                        .detailOptions(Arrays.asList("1", "2", "3"))
                        .build(),
                GetKokContractResponse.ContractOptions.builder()
                        .option("test2")
                        .orderNumber(2)
                        .detailOptions(Arrays.asList("1", "2", "3"))
                        .build()
        );

        return GetKokContractResponse.builder()
                .options(contractOptions)
                .imageInfo(
                        GetKokContractResponse.ImageInfo.builder()
                                .imageNumber(2)
                                .imageUrls(Arrays.asList("test1", "test2", "test3"))
                                .build()
                )
                .build();
    }

    public static GetKokReviewInfoResponse makeTestGetKokReviewInfoResponse() {
        return GetKokReviewInfoResponse.builder()
                .impressions(Arrays.asList("1", "2", "3"))
                .structureStarCount(5)
                .infraStarCount(5)
                .facilityStarCount(5)
                .vibeStarCount(5)
                .reviewText("테스트입니다")
                .build();
    }

    public static GetKokConfigInfoResponse makeTestGetKokConfigInfoResponse() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        List<GetKokConfigInfoResponse.Option> options = Collections.singletonList(
                GetKokConfigInfoResponse.Option.builder()
                        .optionId(1L)
                        .optionTitle("test")
                        .orderNumber(1)
                        .build()
        );
        GetKokConfigInfoResponse.ReviewInfo reviewInfo = GetKokConfigInfoResponse.ReviewInfo.builder()
                .impressions(list)
                .checkedImpressions(list)
                .facilityStarCount(5)
                .infraStarCount(5)
                .vibeStarCount(5)
                .structureStarCount(5)
                .reviewText("테스트입니다")
                .build();

        return GetKokConfigInfoResponse.builder()
                .highlights(list)
                .checkedHighlights(list)
                .furnitureOptions(list)
                .checkedFurnitureOptions(list)
                .reviewInfo(reviewInfo)
                .direction("남쪽")
                .outerImageUrls(list)
                .innerImageUrls(list)
                .contractImageUrls(list)
                .outerOptions(options)
                .innerOptions(options)
                .contractOptions(options)
                .build();
    }

    public static PostOrPutKokResponse makeTestResponsePostOrPutKokResponse(){
        return PostOrPutKokResponse.builder()
                .kokId(1L)
                .build();
    }

    public static MockMultipartFile makeTestRequestPostOrPutKokRequest(){
        return new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());
    }
}

