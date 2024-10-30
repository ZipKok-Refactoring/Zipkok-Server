package com.project.zipkok.kok.response;

import com.project.zipkok.dto.*;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MakeTestKokControllerResponse {

    public static GetKokResponse makeTestGetKokResponse(){
        List<GetKokResponse.Koks> koks = new ArrayList<>();

        koks.add(GetKokResponse.Koks.builder()
                .kokId(1L)
                .realEstateId(1L)
                .imageUrl("test.com")
                .address("testAddress")
                .detailAddress("testDetailAddress")
                .estateAgent("testAgent")
                .transactionType("testMonthly")
                .realEstateType("testOneRoom")
                .deposit(1000L)
                .price(10L)
                .isZimmed(true)
                .build()
        );

        return GetKokResponse.builder()
                .koks(koks)
                .build();
    }

    public static GetKokDetailResponse makeTestGetkokDetailResponse(){

        return GetKokDetailResponse.builder()
                .kokId(1L)
                .imageInfo(
                        GetKokDetailResponse.ImageInfo.builder()
                                .imageNumber(1)
                                .imageUrls(List.of("test1","test2"))
                                .build()
                )
                .realEstateId(1L)
                .address("testAddress")
                .detailAddress("testDetailAddress")
                .transactionType("testMonthly")
                .deposit(1000L)
                .price(10L)
                .detail("testDetail")
                .areaSize(10f)
                .pyeongsu(1)
                .realEstateType("testOneRoom")
                .floorNum(1)
                .administrativeFee(5)
                .latitude(1.1)
                .longitude(1.1)
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

    public static PostOrPutKokResponse makeTestPostOrPutKokResponse(){
        return PostOrPutKokResponse.builder()
                .kokId(1L)
                .build();
    }

    public static MockMultipartFile makeTestPostOrPutKokRequest(){
        return new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());
    }

    public static PostOrPutKokRequest makePostOrPutKokRequest() {
        PostOrPutKokRequest.ReviewInfo reviewInfo = PostOrPutKokRequest.ReviewInfo.builder()
                .checkedImpressions(List.of("testImpression"))
                .facilityStarCount(5)
                .infraStarCount(5)
                .structureStarCount(5)
                .vibeStarCount(5)
                .reviewText("testReview")
                .build();

        List<PostOrPutKokRequest.Option> options_outer = new ArrayList<>();
        List<PostOrPutKokRequest.Option> options_inner = new ArrayList<>();
        List<PostOrPutKokRequest.Option> options_contract = new ArrayList<>();

        options_outer.add(
                PostOrPutKokRequest.Option.builder()
                        .optionId(1L)
                        .checkedDetailOptionIds(List.of(1L, 2L, 3L))
                        .build()
        );

        options_inner.add(
                PostOrPutKokRequest.Option.builder()
                        .optionId(1L)
                        .checkedDetailOptionIds(List.of(1L, 2L, 3L))
                        .build()
        );
        options_contract.add(
                PostOrPutKokRequest.Option.builder()
                        .optionId(1L)
                        .checkedDetailOptionIds(List.of(1L, 2L, 3L))
                        .build()
        );

        return PostOrPutKokRequest.builder()
                .kokId(1L)
                .realEstateId(1L)
                .checkedHighlights(List.of("testHighlight"))
                .checkedFurnitureOptions(List.of("testFurniture"))
                .direction("testDirection")
                .reviewInfo(reviewInfo)
                .checkedOuterOptions(options_outer)
                .checkedInnerOptions(options_inner)
                .checkedContractOptions(options_contract)
                .build();
    }
}

