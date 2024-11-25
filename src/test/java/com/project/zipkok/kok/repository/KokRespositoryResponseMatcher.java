package com.project.zipkok.kok.repository;

import com.project.zipkok.dto.GetKokWithZimStatus;
import com.project.zipkok.model.CheckedDetailOption;
import com.project.zipkok.model.CheckedOption;
import com.project.zipkok.model.Kok;
import org.springframework.data.domain.Slice;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KokRespositoryResponseMatcher {

    public static void expectedRepositoryFindByKokId(Kok response, Kok kok){
        assertEquals(response.getKokId(), kok.getKokId());
        assertEquals(response.getKokImages(), kok.getKokImages());
        assertEquals(response.getCheckedHighlights(), kok.getCheckedHighlights());
        assertEquals(response.getCheckedOptions(), kok.getCheckedOptions());
        assertEquals(response.getCheckedDetailOptions(), kok.getCheckedDetailOptions());
        assertEquals(response.getCheckedFurnitures(), kok.getCheckedFurnitures());
        assertEquals(response.getCheckedImpressions(), kok.getCheckedImpressions());
        assertEquals(response.getDirection(), kok.getDirection());
        assertEquals(response.getReview(), kok.getReview());
    }


    public static void expectedRepositoryFindByUserId(Slice<Kok> response, Kok kok){
        List<Kok> kokList = response.getContent();
        Kok response_kok = kokList.get(0);

        assertEquals(response_kok.getKokId(), kok.getKokId());
        assertEquals(response_kok.getKokImages(), kok.getKokImages());
        assertEquals(response_kok.getCheckedHighlights(), kok.getCheckedHighlights());
        assertEquals(response_kok.getCheckedOptions(), kok.getCheckedOptions());
        assertEquals(response_kok.getCheckedDetailOptions(), kok.getCheckedDetailOptions());
        assertEquals(response_kok.getCheckedFurnitures(), kok.getCheckedFurnitures());
        assertEquals(response_kok.getCheckedImpressions(), kok.getCheckedImpressions());
        assertEquals(response_kok.getDirection(), kok.getDirection());
        assertEquals(response_kok.getReview(), kok.getReview());
    }

    public static void expectedRepositoryGetKokWithZimStatus(List<GetKokWithZimStatus> response, Kok kok){
        Kok response_kok = response.get(0).getKok();
        boolean response_zim = response.get(0).getZimStatus();

        assertEquals(response_kok.getKokId(), kok.getKokId());
        assertEquals(response_kok.getKokImages(), kok.getKokImages());
        assertEquals(response_kok.getCheckedHighlights(), kok.getCheckedHighlights());
        assertEquals(response_kok.getCheckedOptions(), kok.getCheckedOptions());
        assertEquals(response_kok.getCheckedDetailOptions(), kok.getCheckedDetailOptions());
        assertEquals(response_kok.getCheckedFurnitures(), kok.getCheckedFurnitures());
        assertEquals(response_kok.getCheckedImpressions(), kok.getCheckedImpressions());
        assertEquals(response_kok.getDirection(), kok.getDirection());
        assertEquals(response_kok.getReview(), kok.getReview());
        assertTrue(response_zim);
    }

    public static void expectedFindKokWithCheckedOptionAndCheckedDetailOption(Kok response, Kok kok, CheckedOption checkedOption, CheckedDetailOption checkedDetailOption){
        assertEquals(response.getKokId(), kok.getKokId());
        assertEquals(response.getKokImages(), kok.getKokImages());
        assertEquals(response.getCheckedHighlights(), kok.getCheckedHighlights());
        assertEquals(response.getDirection(), kok.getDirection());
        assertEquals(response.getReview(), kok.getReview());

        assertEquals(response.getCheckedOptions().iterator().next().getCheckedOptionId(), checkedOption.getCheckedOptionId());
        assertEquals(response.getCheckedDetailOptions().iterator().next().getCheckedDetailOptionId(), checkedDetailOption.getCheckedDetailOptionId());
    }

    public static void expectedFindKokWithImpressionAndStar(Kok response, Kok kok){
        assertEquals(response.getKokId(), kok.getKokId());
        assertEquals(response.getKokImages(), kok.getKokImages());
        assertEquals(response.getCheckedHighlights(), kok.getCheckedHighlights());
        assertEquals(response.getCheckedFurnitures(), kok.getCheckedFurnitures());
        assertEquals(response.getDirection(), kok.getDirection());
        assertEquals(response.getReview(), kok.getReview());

        assertEquals(response.getStar().getStarId(), 6);
        assertEquals(response.getStar().getFacilityStar(), 5);
        assertEquals(response.getStar().getInfraStar(), 5);
        assertEquals(response.getStar().getVibeStar(), 5);
        assertEquals(response.getStar().getStructureStar(), 5);
    }
}
