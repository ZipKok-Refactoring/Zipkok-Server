package com.project.zipkok.kok.service;

import com.project.zipkok.dto.*;
import com.project.zipkok.model.KokImage;
import com.project.zipkok.model.RealEstate;

import java.util.Optional;

import static com.project.zipkok.kok.fixture.CheckedFixture.*;
import static com.project.zipkok.kok.fixture.KokFixture.KOK_01;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KokServiceResponseMatcher {

    public static void expectedServiceGetKokResponse(GetKokResponse response){
        GetKokResponse.Koks kok = response.getKoks().get(0);
        RealEstate realEstate = KOK_01.getRealEstate();

        assertNotNull(response.getKoks());
        assertEquals(response.getKoks().size(), 1);
        assertEquals(KOK_01.getKokId(), kok.getKokId());
        assertEquals(realEstate.getRealEstateId(), kok.getRealEstateId());
        assertEquals(KOK_01.getKokImages().get(0).getImageUrl(), kok.getImageUrl());
        assertEquals(realEstate.getAddress(), kok.getAddress());
        assertEquals(realEstate.getDetailAddress(), kok.getDetailAddress());
        assertEquals(realEstate.getAgent(), kok.getEstateAgent());
        assertEquals(realEstate.getTransactionType().getDescription(), kok.getTransactionType());
        assertEquals(realEstate.getRealEstateType().getDescription(), kok.getRealEstateType());
        assertEquals(realEstate.getDeposit(), kok.getDeposit());
        assertEquals(realEstate.getPrice(), kok.getPrice());
    }

    public static void expectedServiceGetKokDetailResponse(GetKokDetailResponse response){
        RealEstate realEstate = KOK_01.getRealEstate();

        assertEquals(KOK_01.getKokId(), response.getKokId());
        assertEquals(KOK_01.getRealEstate().getRealEstateId(), response.getRealEstateId());

        assertEquals(1, response.getImageInfo().getImageNumber());
        assertEquals(response.getImageInfo().getImageUrls().size() , 1);
        assertEquals(response.getImageInfo().getImageUrls().get(0) , "https://test.com");

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

    public static void expectedServiceGetKokOuterInfoResponse(GetKokOuterInfoResponse response){
        assertEquals(response.getHighlights().size(), 1);
        assertEquals(response.getOptions().size(), 1);
        assertEquals(DUMMY_OPTION_OUTER.getName(), response.getOptions().get(0).getOption());
        assertEquals(DUMMY_OPTION_OUTER.getOrderNum(), response.getOptions().get(0).getOrderNumber());
        assertEquals(DUMMY_OPTION_OUTER.getOrderNum(), response.getOptions().get(0).getOrderNumber());
        assertEquals(response.getOptions().get(0).getDetailOptions().size(),1);
    }

    public static void expectedServiceGetKokInnerInfoResponse(GetKokInnerInfoResponse response){
        assertEquals(response.getFurnitureOptions().size(), 1);
        assertEquals(response.getOptions().size(), 1);
        assertEquals(DUMMY_OPTION_INNER.getName(), response.getOptions().get(0).getOption());
        assertEquals(DUMMY_OPTION_INNER.getOrderNum(), response.getOptions().get(0).getOrderNumber());
        assertEquals(DUMMY_OPTION_INNER.getOrderNum(), response.getOptions().get(0).getOrderNumber());
        assertEquals(response.getOptions().get(0).getDetailOptions().size(),1);
    }

    public static void expectedServiceGetKokContractInfoResponse(GetKokContractResponse response){
        assertEquals(response.getOptions().size(), 1);
        assertEquals(DUMMY_OPTION_CONTRACT.getName(), response.getOptions().get(0).getOption());
        assertEquals(DUMMY_OPTION_CONTRACT.getOrderNum(), response.getOptions().get(0).getOrderNumber());
        assertEquals(DUMMY_OPTION_CONTRACT.getOrderNum(), response.getOptions().get(0).getOrderNumber());
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

    public static void expectedServiceGetKokConfigInfoResponse(GetKokConfigInfoResponse response){
        assertEquals(response.getHighlights().size(), 1);
        assertEquals(response.getCheckedHighlights().size(), 1);
        assertEquals(response.getFurnitureOptions().size(), 1);
        assertEquals(response.getCheckedFurnitureOptions().size(), 1);
        assertEquals(response.getDirection(), KOK_01.getDirection());
        assertEquals(response.getOuterOptions().size(), 1);
        assertEquals(response.getInnerOptions().size(), 1);
        assertEquals(response.getContractOptions().size(), 1);
    }

    public static void expectedServicePostOrPutKokResponse(PostOrPutKokResponse response){
        assertEquals(response.getKokId(), KOK_01.getKokId());
    }

}
