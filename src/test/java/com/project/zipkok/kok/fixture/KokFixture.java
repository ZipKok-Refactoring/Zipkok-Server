package com.project.zipkok.kok.fixture;

import com.project.zipkok.model.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Set;

import static com.project.zipkok.kok.fixture.CheckedFixture.*;
import static com.project.zipkok.kok.fixture.KokImageFixture.DUMMY_KOK_IMAGE;
import static com.project.zipkok.kok.fixture.RealEstateFixture.DUMMY_REALSTATE;
import static com.project.zipkok.kok.fixture.StarFixture.DUMMY_STAR;
import static com.project.zipkok.kok.fixture.UserFixture.DUMMY_USER;

public class KokFixture {

    public static final Kok KOK_01 = Kok.builder()
            .direction("동쪽")
            .review("테스트입니다")
            .build();

    static {
        ReflectionTestUtils.setField(KOK_01, "kokId", 1L);
        ReflectionTestUtils.setField(KOK_01, "realEstate", DUMMY_REALSTATE);
        ReflectionTestUtils.setField(KOK_01, "user", DUMMY_USER);
        ReflectionTestUtils.setField(KOK_01, "checkedFurnitures", List.of(DUMMY_CHECKED_FURNITURE));
        ReflectionTestUtils.setField(KOK_01, "checkedImpressions", List.of(DUMMY_CHECKED_IMPRESSION));
        ReflectionTestUtils.setField(KOK_01, "checkedHighlights", List.of(DUMMY_CHECKED_HIGHLIGHT));
        ReflectionTestUtils.setField(KOK_01, "checkedDetailOptions", Set.of(DUMMY_CHECKED_DETAIL_OPTION, DUMMY_CHECKED_DETAIL_OPTION2, DUMMY_CHECKED_DETAIL_OPTION3));
        ReflectionTestUtils.setField(KOK_01, "checkedOptions", Set.of(DUMMY_CHECKED_OPTION, DUMMY_CHECKED_OPTION2, DUMMY_CHECKED_OPTION3));
        ReflectionTestUtils.setField(KOK_01, "kokImages", List.of(DUMMY_KOK_IMAGE));
        ReflectionTestUtils.setField(KOK_01, "star", DUMMY_STAR);
    }

}
