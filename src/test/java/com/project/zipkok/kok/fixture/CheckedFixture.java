package com.project.zipkok.kok.fixture;

import com.project.zipkok.model.*;
import org.springframework.test.util.ReflectionTestUtils;

public class CheckedFixture {

    public static final CheckedFurniture DUMMY_CHECKED_FURNITURE = new CheckedFurniture();
    public static final FurnitureOption DUMMY_FURNITURE_OPTION = new FurnitureOption();

    public static final CheckedImpression DUMMY_CHECKED_IMPRESSION = new CheckedImpression();
    public static final Impression DUMMY_IMPRESSION = new Impression();

    public static final CheckedHighlight DUMMY_CHECKED_HIGHLIGHT = new CheckedHighlight();
    public static final Highlight DUMMY_HIGHLIGHT = new Highlight();

    public static final CheckedOption DUMMY_CHECKED_OPTION = new CheckedOption();
    public static final Option DUMMY_OPTION = new Option();

    public static final CheckedDetailOption DUMMY_CHECKED_DETAIL_OPTION = new CheckedDetailOption();
    public static final DetailOption DUMMY_DETAIL_OPTION = new DetailOption();

    static{
        //checkedFurniture
        ReflectionTestUtils.setField(DUMMY_CHECKED_FURNITURE, "checkedFurnitureId", 1L);

        ReflectionTestUtils.setField(DUMMY_FURNITURE_OPTION, "furnitureOptionId", 1L);
        ReflectionTestUtils.setField(DUMMY_CHECKED_FURNITURE, "furnitureOption", DUMMY_FURNITURE_OPTION);

        //checkedImpression
        ReflectionTestUtils.setField(DUMMY_CHECKED_IMPRESSION, "checkedImpressionId", 1L);

        ReflectionTestUtils.setField(DUMMY_IMPRESSION, "impressionId", 1L);
        ReflectionTestUtils.setField(DUMMY_CHECKED_IMPRESSION, "impression", DUMMY_IMPRESSION);

        //checkedHighlight
        ReflectionTestUtils.setField(DUMMY_CHECKED_HIGHLIGHT, "checkedHighlightId", 1L);

        ReflectionTestUtils.setField(DUMMY_HIGHLIGHT, "highlightId", 1L);
        ReflectionTestUtils.setField(DUMMY_CHECKED_HIGHLIGHT, "highlight", DUMMY_HIGHLIGHT);

        //checkedOption
        ReflectionTestUtils.setField(DUMMY_CHECKED_OPTION, "checkedOptionId", 1L);

        ReflectionTestUtils.setField(DUMMY_OPTION, "optionId", 1L);
        ReflectionTestUtils.setField(DUMMY_CHECKED_OPTION, "option", DUMMY_OPTION);

        //checkedDetailOption
        ReflectionTestUtils.setField(DUMMY_CHECKED_DETAIL_OPTION, "checkedDetailOptionId", 1L);

        ReflectionTestUtils.setField(DUMMY_DETAIL_OPTION, "detailOptionId", 1L);
        ReflectionTestUtils.setField(DUMMY_CHECKED_DETAIL_OPTION, "detailOption", DUMMY_DETAIL_OPTION);
    }
}
