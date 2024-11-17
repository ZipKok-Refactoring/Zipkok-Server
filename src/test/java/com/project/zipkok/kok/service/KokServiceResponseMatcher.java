package com.project.zipkok.kok.service;

import com.project.zipkok.dto.*;
import com.project.zipkok.model.Kok;
import com.project.zipkok.model.Option;
import com.project.zipkok.model.RealEstate;

import static com.project.zipkok.kok.fixture.KokFixture.KOK_01;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KokServiceResponseMatcher {

    public static void expectedServiceGetKokResponse(GetKokResponse response, Kok testKok){
        GetKokResponse.Koks kok = response.getKoks().get(0);
        RealEstate realEstate = testKok.getRealEstate();

        assertNotNull(response.getKoks());
        assertEquals(response.getKoks().size(), 1);
        assertEquals(testKok.getKokId(), kok.getKokId());
        assertEquals(realEstate.getRealEstateId(), kok.getRealEstateId());
        assertEquals(testKok.getKokImages().get(0).getImageUrl(), kok.getImageUrl());
        assertEquals(realEstate.getAddress(), kok.getAddress());
        assertEquals(realEstate.getDetailAddress(), kok.getDetailAddress());
        assertEquals(realEstate.getAgent(), kok.getEstateAgent());
        assertEquals(realEstate.getTransactionType().getDescription(), kok.getTransactionType());
        assertEquals(realEstate.getRealEstateType().getDescription(), kok.getRealEstateType());
        assertEquals(realEstate.getDeposit(), kok.getDeposit());
        assertEquals(realEstate.getPrice(), kok.getPrice());
    }

    public static void expectedServiceGetKokDetailResponse(GetKokDetailResponse response, Kok kok){
        RealEstate realEstate = kok.getRealEstate();

        assertEquals(kok.getKokId(), response.getKokId());
        assertEquals(kok.getRealEstate().getRealEstateId(), response.getRealEstateId());

        assertEquals(1, response.getImageInfo().getImageNumber());
        assertEquals(response.getImageInfo().getImageUrls().size() , 1);
        assertEquals(response.getImageInfo().getImageUrls().get(0) , "test/test");

        assertEquals(realEstate.getAddress(), response.getAddress());
        assertEquals(realEstate.getDetailAddress(), response.getDetailAddress());
        assertEquals(realEstate.getTransactionType().toString(), response.getTransactionType());
        assertEquals(realEstate.getRealEstateType().toString(), response.getRealEstateType());
        assertEquals(realEstate.getDeposit(), response.getDeposit());
        assertEquals(realEstate.getPrice(), response.getPrice());
        assertEquals(realEstate.getDetail(), response.getDetail());
        assertEquals(realEstate.getPyeongsu(), Integer.toUnsignedLong(response.getPyeongsu()));
        assertEquals(realEstate.getAreaSize(), response.getAreaSize());
        assertEquals(realEstate.getFloorNum(), response.getFloorNum());
        assertEquals(realEstate.getAdministrativeFee(), response.getAdministrativeFee());
        assertEquals(realEstate.getLatitude(), response.getLatitude());
        assertEquals(realEstate.getLongitude(), response.getLongitude());
    }

    public static void expectedServiceGetKokOuterInfoResponse(GetKokOuterInfoResponse response, Kok kok){
        Option option = kok.getCheckedOptions().iterator().next().getOption();

        assertEquals(response.getHighlights().size(), 1);
        assertEquals(response.getOptions().size(), 1);
        assertEquals(option.getName(), response.getOptions().get(0).getOption());
        assertEquals(option.getOrderNum(), response.getOptions().get(0).getOrderNumber());
        assertEquals(option.getOrderNum(), response.getOptions().get(0).getOrderNumber());
        assertEquals(response.getOptions().get(0).getDetailOptions().size(),1);
    }

    public static void expectedServiceGetKokInnerInfoResponse(GetKokInnerInfoResponse response, Kok kok){
        Option option = kok.getCheckedOptions().iterator().next().getOption();

        assertEquals(response.getFurnitureOptions().size(), 1);
        assertEquals(response.getOptions().size(), 1);
        assertEquals(option.getName(), response.getOptions().get(0).getOption());
        assertEquals(option.getOrderNum(), response.getOptions().get(0).getOrderNumber());
        assertEquals(option.getOrderNum(), response.getOptions().get(0).getOrderNumber());
        assertEquals(response.getOptions().get(0).getDetailOptions().size(),1);
    }

    public static void expectedServiceGetKokContractInfoResponse(GetKokContractResponse response, Kok kok){
        Option option = kok.getCheckedOptions().iterator().next().getOption();

        assertEquals(response.getOptions().size(), 1);
        assertEquals(option.getName(), response.getOptions().get(0).getOption());
        assertEquals(option.getOrderNum(), response.getOptions().get(0).getOrderNumber());
        assertEquals(option.getOrderNum(), response.getOptions().get(0).getOrderNumber());
        assertEquals(response.getOptions().get(0).getDetailOptions().size(),1);
    }

    public static void expectedServiceGetKokReviewInfoResponse(GetKokReviewInfoResponse response){
        assertEquals(response.getImpressions().size(), 1);
        assertEquals(response.getFacilityStarCount(), 5);
        assertEquals(response.getInfraStarCount(), 5);
        assertEquals(response.getVibeStarCount(), 5);
        assertEquals(response.getStructureStarCount(), 5);
        assertEquals(response.getReviewText(), "테스트입니다");
    }

    public static void expectedServiceGetKokConfigInfoResponse(GetKokConfigInfoResponse response, Kok kok){
        assertEquals(response.getHighlights().size(), 1);
        assertEquals(response.getCheckedHighlights().size(), 1);
        assertEquals(response.getFurnitureOptions().size(), 1);
        assertEquals(response.getCheckedFurnitureOptions().size(), 1);
        assertEquals(response.getDirection(), kok.getDirection());
        assertEquals(response.getOuterOptions().size(), 1);
        assertEquals(response.getInnerOptions().size(), 1);
        assertEquals(response.getContractOptions().size(), 1);
    }

    public static void expectedServicePostOrPutKokResponse(PostOrPutKokResponse response){
        assertEquals(response.getKokId(), KOK_01.getKokId());
    }

}
