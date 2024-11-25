package com.project.zipkok.kok.response;

import com.project.zipkok.dto.*;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MakeTestKokControllerResponse {

    public static GetKokResponse makeTestGetKokResponse(
            Long id,
            Long rsId,
            String url,
            String address,
            String dAddres,
            String agent,
            String transType,
            String realType,
            Long deposit,
            Long price,
            boolean isZimmed
    ){
        List<GetKokResponse.Koks> koks = new ArrayList<>();

        koks.add(GetKokResponse.Koks.builder()
                .kokId(id)
                .realEstateId(rsId)
                .imageUrl(url)
                .address(address)
                .detailAddress(dAddres)
                .estateAgent(agent)
                .transactionType(transType)
                .realEstateType(realType)
                .deposit(deposit)
                .price(price)
                .isZimmed(isZimmed)
                .build()
        );

        return GetKokResponse.builder()
                .koks(koks)
                .build();
    }

    public static GetKokDetailResponse makeTestGetkokDetailResponse(
            Long id,
            int imageNum,
            List<String> imageUrls,
            Long reId,
            String address,
            String dAddress,
            String transType,
            Long deposit,
            Long price,
            String detail,
            float area,
            int pyeongsu,
            String reType,
            int floorNum,
            int adFee,
            double latitude,
            double longitude
    ){

        return GetKokDetailResponse.builder()
                .kokId(id)
                .imageInfo(
                        GetKokDetailResponse.ImageInfo.builder()
                                .imageNumber(imageNum)
                                .imageUrls(imageUrls)
                                .build()
                )
                .realEstateId(reId)
                .address(address)
                .detailAddress(dAddress)
                .transactionType(transType)
                .deposit(deposit)
                .price(price)
                .detail(detail)
                .areaSize(area)
                .pyeongsu(pyeongsu)
                .realEstateType(reType)
                .floorNum(floorNum)
                .administrativeFee(adFee)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    public static GetKokOuterInfoResponse makeTestGetKokOuterInfoResponse(
            List<String> highlight,
            List<String> options,
            List<Integer> orderNumber,
            List<String> detailOptions
    ){
        List<String> highlights = highlight;
        List<GetKokOuterInfoResponse.OuterOption> outerOptions = Arrays.asList(
                GetKokOuterInfoResponse.OuterOption.builder()
                        .option(options.get(0))
                        .orderNumber(orderNumber.get(0))
                        .detailOptions(detailOptions)
                        .build(),
                GetKokOuterInfoResponse.OuterOption.builder()
                        .option(options.get(1))
                        .orderNumber(orderNumber.get(1))
                        .detailOptions(detailOptions)
                        .build()
        );

        return GetKokOuterInfoResponse.builder()
                .highlights(highlights)
                .options(outerOptions)
                .build();
    }

    public static GetKokInnerInfoResponse makeTestGetKokInnerInfoResponse(
            List<String> furniture,
            List<String> options,
            List<Integer> orderNumber,
            List<String> detailOptions,
            String direction
    ){
        List<String> furnitures =furniture;
        List<GetKokInnerInfoResponse.InnerOption> innerOptions = Arrays.asList(
                GetKokInnerInfoResponse.InnerOption.builder()
                        .option(options.get(0))
                        .orderNumber(orderNumber.get(0))
                        .detailOptions(detailOptions)
                        .build(),
                GetKokInnerInfoResponse.InnerOption.builder()
                        .option(options.get(1))
                        .orderNumber(orderNumber.get(1))
                        .detailOptions(detailOptions)
                        .build()
        );

        return GetKokInnerInfoResponse.builder()
                .furnitureOptions(furnitures)
                .direction(direction)
                .options(innerOptions)
                .build();
    }

    public static GetKokContractResponse makeTestGetKokContractResponse(
            List<String> options,
            List<Integer> orderNumber,
            List<String> detailOptions,
            int imageNumber,
            List<String> imageUrl
    ) {
        List<GetKokContractResponse.ContractOptions> contractOptions = Arrays.asList(
                GetKokContractResponse.ContractOptions.builder()
                        .option(options.get(0))
                        .orderNumber(orderNumber.get(0))
                        .detailOptions(detailOptions)
                        .build(),
                GetKokContractResponse.ContractOptions.builder()
                        .option(options.get(1))
                        .orderNumber(orderNumber.get(1))
                        .detailOptions(detailOptions)
                        .build()
        );

        return GetKokContractResponse.builder()
                .options(contractOptions)
                .imageInfo(
                        GetKokContractResponse.ImageInfo.builder()
                                .imageNumber(imageNumber)
                                .imageUrls(imageUrl)
                                .build()
                )
                .build();
    }

    public static GetKokReviewInfoResponse makeTestGetKokReviewInfoResponse(
            List<String> impressions,
            int structureStarCount,
            int infraStarCount,
            int facilityStarCount,
            int vibeStarCount,
            String text
    ) {
        return GetKokReviewInfoResponse.builder()
                .impressions(impressions)
                .structureStarCount(structureStarCount)
                .infraStarCount(infraStarCount)
                .facilityStarCount(facilityStarCount)
                .vibeStarCount(vibeStarCount)
                .reviewText(text)
                .build();
    }

    public static GetKokConfigInfoResponse makeTestGetKokConfigInfoResponse(
            List<String> lists,
            Long id, String optionTitle, int orderNumber,
            int structureStarCount, int infraStarCount, int facilityStarCount, int vibeStarCount,String text,
            String direction
    ) {
        List<String> list = lists;
        List<GetKokConfigInfoResponse.Option> options = Collections.singletonList(
                GetKokConfigInfoResponse.Option.builder()
                        .optionId(id)
                        .optionTitle(optionTitle)
                        .orderNumber(orderNumber)
                        .build()
        );
        GetKokConfigInfoResponse.ReviewInfo reviewInfo = GetKokConfigInfoResponse.ReviewInfo.builder()
                .impressions(list)
                .checkedImpressions(list)
                .facilityStarCount(facilityStarCount)
                .infraStarCount(infraStarCount)
                .vibeStarCount(vibeStarCount)
                .structureStarCount(structureStarCount)
                .reviewText(text)
                .build();

        return GetKokConfigInfoResponse.builder()
                .highlights(list)
                .checkedHighlights(list)
                .furnitureOptions(list)
                .checkedFurnitureOptions(list)
                .reviewInfo(reviewInfo)
                .direction(direction)
                .outerImageUrls(list)
                .innerImageUrls(list)
                .contractImageUrls(list)
                .outerOptions(options)
                .innerOptions(options)
                .contractOptions(options)
                .build();
    }

    public static PostOrPutKokResponse makeTestPostOrPutKokResponse(Long id){
        return PostOrPutKokResponse.builder()
                .kokId(id)
                .build();
    }

    public static MockMultipartFile makeTestPostOrPutKokRequest(String file, String fileName, String contentType, String test){
        return new MockMultipartFile(file, fileName, contentType, test.getBytes());
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

