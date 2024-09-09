package com.project.zipkok.kok.controller;

import com.project.zipkok.dto.*;
import com.project.zipkok.model.Kok;
import com.project.zipkok.model.KokImage;
import com.project.zipkok.model.RealEstate;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.project.zipkok.kok.fixture.KokFixture.KOK_01;

public class MakeTestKokControllerResponse {

    public static final Kok kok = KOK_01;

    public static GetKokResponse makeTestGetKokResponse(){
        List<GetKokResponse.Koks> koks = new ArrayList<>();

        RealEstate realEstate = kok.getRealEstate();

        koks.add(GetKokResponse.Koks.builder()
                .kokId(kok.getKokId())
                .realEstateId(realEstate.getRealEstateId())
                .imageUrl(kok.getKokImages().get(0).getImageUrl())
                .address(realEstate.getAddress())
                .detailAddress(realEstate.getDetailAddress())
                .estateAgent(realEstate.getAgent())
                .transactionType(realEstate.getTransactionType().getDescription())
                .realEstateType(realEstate.getRealEstateType().getDescription())
                .deposit(realEstate.getDeposit())
                .price(realEstate.getPrice())
                .isZimmed(true)
                .build()
        );

        return GetKokResponse.builder()
                .koks(koks)
                .build();
    }

    public static GetKokDetailResponse makeTestGetkokDetailResponse(){

        RealEstate realEstate = kok.getRealEstate();

        return GetKokDetailResponse.builder()
                .kokId(kok.getKokId())
                .imageInfo(
                        GetKokDetailResponse.ImageInfo.builder()
                                .imageNumber(1)
                                .imageUrls(kok.getKokImages().stream().map(KokImage::getImageUrl).toList())
                                .build()
                )
                .realEstateId(realEstate.getRealEstateId())
                .address(realEstate.getAddress())
                .detailAddress(realEstate.getDetailAddress())
                .transactionType(realEstate.getTransactionType().getDescription())
                .deposit(realEstate.getDeposit())
                .price(realEstate.getPrice())
                .detail(realEstate.getDetail())
                .areaSize(realEstate.getAreaSize())
                .pyeongsu((int)realEstate.getPyeongsu())
                .realEstateType(realEstate.getRealEstateType().getDescription())
                .floorNum(realEstate.getFloorNum())
                .administrativeFee(realEstate.getAdministrativeFee())
                .latitude(realEstate.getLatitude())
                .longitude(realEstate.getLongitude())
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
}

