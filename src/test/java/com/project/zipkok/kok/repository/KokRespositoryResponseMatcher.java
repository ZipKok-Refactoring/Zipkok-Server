package com.project.zipkok.kok.repository;

import com.project.zipkok.dto.GetKokWithZimStatus;
import com.project.zipkok.model.Kok;
import com.project.zipkok.model.Zim;
import org.springframework.data.domain.Slice;

import java.util.List;

import static com.project.zipkok.kok.fixture.CheckedFixture.*;
import static com.project.zipkok.kok.fixture.KokFixture.KOK_01;
import static com.project.zipkok.kok.fixture.UserFixture.DUMMY_ZIM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KokRespositoryResponseMatcher {

    public static void expectedRepositoryFindByKokId(Kok response){
        Kok kok = KOK_01;

        assertEquals(response.getKokId(), kok.getKokId());
        assertEquals(response.getKokImages(), kok.getKokImages());
        assertEquals(response.getCheckedHighlights(), kok.getCheckedHighlights());
        assertEquals(response.getRealEstate(), kok.getRealEstate());
        assertEquals(response.getCheckedOptions(), kok.getCheckedOptions());
        assertEquals(response.getCheckedDetailOptions(), kok.getCheckedDetailOptions());
        assertEquals(response.getCheckedFurnitures(), kok.getCheckedFurnitures());
        assertEquals(response.getCheckedImpressions(), kok.getCheckedImpressions());
        assertEquals(response.getDirection(), kok.getDirection());
        assertEquals(response.getReview(), kok.getReview());
    }


    public static void expectedRepositoryFindByUserId(Slice<Kok> response){
        List<Kok> kokList = response.getContent();
        Kok response_kok = kokList.get(0);
        Kok kok = KOK_01;

        assertEquals(response_kok.getKokId(), kok.getKokId());
        assertEquals(response_kok.getKokImages(), kok.getKokImages());
        assertEquals(response_kok.getCheckedHighlights(), kok.getCheckedHighlights());
        assertEquals(response_kok.getRealEstate(), kok.getRealEstate());
        assertEquals(response_kok.getCheckedOptions(), kok.getCheckedOptions());
        assertEquals(response_kok.getCheckedDetailOptions(), kok.getCheckedDetailOptions());
        assertEquals(response_kok.getCheckedFurnitures(), kok.getCheckedFurnitures());
        assertEquals(response_kok.getCheckedImpressions(), kok.getCheckedImpressions());
        assertEquals(response_kok.getDirection(), kok.getDirection());
        assertEquals(response_kok.getReview(), kok.getReview());
    }

    public static void expectedRepositoryGetKokWithZimStatus(List<GetKokWithZimStatus> response){
        Kok response_kok = response.get(0).getKok();
        boolean response_zim = response.get(0).getZimStatus();
        Kok kok = KOK_01;
        Zim zim = DUMMY_ZIM;

        assertEquals(response_kok.getKokId(), kok.getKokId());
        assertEquals(response_kok.getKokImages(), kok.getKokImages());
        assertEquals(response_kok.getCheckedHighlights(), kok.getCheckedHighlights());
        assertEquals(response_kok.getRealEstate(), kok.getRealEstate());
        assertEquals(response_kok.getCheckedOptions(), kok.getCheckedOptions());
        assertEquals(response_kok.getCheckedDetailOptions(), kok.getCheckedDetailOptions());
        assertEquals(response_kok.getCheckedFurnitures(), kok.getCheckedFurnitures());
        assertEquals(response_kok.getCheckedImpressions(), kok.getCheckedImpressions());
        assertEquals(response_kok.getDirection(), kok.getDirection());
        assertEquals(response_kok.getReview(), kok.getReview());
        assertTrue(response_zim);
    }

    public static void expectedFindKokWithCheckedOptionAndCheckedDetailOption(Kok response){
        Kok kok = KOK_01;

        assertEquals(response.getKokId(), kok.getKokId());
        assertEquals(response.getKokImages(), kok.getKokImages());
        assertEquals(response.getCheckedHighlights(), kok.getCheckedHighlights());
        assertEquals(response.getRealEstate(), kok.getRealEstate());
        assertEquals(response.getCheckedOptions(), kok.getCheckedOptions());
        assertEquals(response.getCheckedDetailOptions(), kok.getCheckedDetailOptions());
        assertEquals(response.getCheckedFurnitures(), kok.getCheckedFurnitures());
        assertEquals(response.getCheckedImpressions(), kok.getCheckedImpressions());
        assertEquals(response.getDirection(), kok.getDirection());
        assertEquals(response.getReview(), kok.getReview());

        assertTrue(response.getCheckedOptions().contains(DUMMY_CHECKED_OPTION));
        assertTrue(response.getCheckedOptions().contains(DUMMY_CHECKED_OPTION2));
        assertTrue(response.getCheckedOptions().contains(DUMMY_CHECKED_OPTION3));
        assertTrue(response.getCheckedDetailOptions().contains(DUMMY_CHECKED_DETAIL_OPTION));
        assertTrue(response.getCheckedDetailOptions().contains(DUMMY_CHECKED_DETAIL_OPTION2));
        assertTrue(response.getCheckedDetailOptions().contains(DUMMY_CHECKED_DETAIL_OPTION3));
    }

    public static void expectedFindKokWithImpressionAndStar(Kok response){
        Kok kok = KOK_01;

        assertEquals(response.getKokId(), kok.getKokId());
        assertEquals(response.getKokImages(), kok.getKokImages());
        assertEquals(response.getCheckedHighlights(), kok.getCheckedHighlights());
        assertEquals(response.getRealEstate(), kok.getRealEstate());
        assertEquals(response.getCheckedOptions(), kok.getCheckedOptions());
        assertEquals(response.getCheckedDetailOptions(), kok.getCheckedDetailOptions());
        assertEquals(response.getCheckedFurnitures(), kok.getCheckedFurnitures());
        assertEquals(response.getCheckedImpressions(), kok.getCheckedImpressions());
        assertEquals(response.getDirection(), kok.getDirection());
        assertEquals(response.getReview(), kok.getReview());

        assertEquals(response.getStar().getStarId(), 1);
        assertEquals(response.getStar().getFacilityStar(), 5);
        assertEquals(response.getStar().getInfraStar(), 5);
        assertEquals(response.getStar().getVibeStar(), 5);
        assertEquals(response.getStar().getStructureStar(), 5);
    }
}
