package com.project.zipkok.kok.fixture;

import com.project.zipkok.common.enums.OptionCategory;
import com.project.zipkok.model.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Set;

import static com.project.zipkok.kok.fixture.UserFixture.DUMMY_USER;

public class CheckedFixture {

    public static final CheckedFurniture DUMMY_CHECKED_FURNITURE = new CheckedFurniture();
    public static final FurnitureOption DUMMY_FURNITURE_OPTION = new FurnitureOption("테스트입니다", "테스트입니다");

    public static final CheckedImpression DUMMY_CHECKED_IMPRESSION = new CheckedImpression();
    public static final Impression DUMMY_IMPRESSION = new Impression("테스트입니다");

    public static final CheckedHighlight DUMMY_CHECKED_HIGHLIGHT = new CheckedHighlight();
    public static final Highlight DUMMY_HIGHLIGHT = new Highlight(DUMMY_USER, "테스트입니다");

    public static final CheckedOption DUMMY_CHECKED_OPTION = new CheckedOption();
    public static final CheckedOption DUMMY_CHECKED_OPTION2 = new CheckedOption();
    public static final CheckedOption DUMMY_CHECKED_OPTION3 = new CheckedOption();
    public static final Option DUMMY_OPTION_OUTER = new Option("test_outer", true, 1, OptionCategory.OUTER, DUMMY_USER);
    public static final Option DUMMY_OPTION_INNER = new Option("test_inner", true, 2, OptionCategory.INNER, DUMMY_USER);
    public static final Option DUMMY_OPTION_CONTRACT = new Option("test_contract", true, 3, OptionCategory.CONTRACT, DUMMY_USER);

    public static final CheckedDetailOption DUMMY_CHECKED_DETAIL_OPTION = new CheckedDetailOption();
    public static final CheckedDetailOption DUMMY_CHECKED_DETAIL_OPTION2 = new CheckedDetailOption();
    public static final CheckedDetailOption DUMMY_CHECKED_DETAIL_OPTION3 = new CheckedDetailOption();
    public static final DetailOption DUMMY_DETAIL_OPTION_OUTER = new DetailOption("test", true, DUMMY_OPTION_OUTER);
    public static final DetailOption DUMMY_DETAIL_OPTION_INNER = new DetailOption("test", true, DUMMY_OPTION_INNER);
    public static final DetailOption DUMMY_DETAIL_OPTION_CONTRACT = new DetailOption("test", true, DUMMY_OPTION_CONTRACT);

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
        ReflectionTestUtils.setField(DUMMY_CHECKED_OPTION2, "checkedOptionId", 2L);
        ReflectionTestUtils.setField(DUMMY_CHECKED_OPTION3, "checkedOptionId", 3L);

        ReflectionTestUtils.setField(DUMMY_OPTION_OUTER, "optionId", 1L);
        ReflectionTestUtils.setField(DUMMY_CHECKED_OPTION, "option", DUMMY_OPTION_OUTER);
        ReflectionTestUtils.setField(DUMMY_OPTION_INNER, "optionId", 2L);
        ReflectionTestUtils.setField(DUMMY_CHECKED_OPTION2, "option", DUMMY_OPTION_INNER);
        ReflectionTestUtils.setField(DUMMY_OPTION_CONTRACT, "optionId", 3L);
        ReflectionTestUtils.setField(DUMMY_CHECKED_OPTION3, "option", DUMMY_OPTION_CONTRACT);

        ReflectionTestUtils.setField(DUMMY_OPTION_OUTER, "detailOptions", Set.of(DUMMY_DETAIL_OPTION_OUTER));
        ReflectionTestUtils.setField(DUMMY_OPTION_INNER, "detailOptions", Set.of(DUMMY_DETAIL_OPTION_INNER));
        ReflectionTestUtils.setField(DUMMY_OPTION_CONTRACT, "detailOptions", Set.of(DUMMY_DETAIL_OPTION_CONTRACT));

        //checkedDetailOption
        ReflectionTestUtils.setField(DUMMY_CHECKED_DETAIL_OPTION, "checkedDetailOptionId", 1L);

        ReflectionTestUtils.setField(DUMMY_DETAIL_OPTION_OUTER, "detailOptionId", 1L);
        ReflectionTestUtils.setField(DUMMY_DETAIL_OPTION_INNER, "detailOptionId", 2L);
        ReflectionTestUtils.setField(DUMMY_DETAIL_OPTION_CONTRACT, "detailOptionId", 3L);
        ReflectionTestUtils.setField(DUMMY_CHECKED_DETAIL_OPTION, "detailOption", DUMMY_DETAIL_OPTION_OUTER);
        ReflectionTestUtils.setField(DUMMY_CHECKED_DETAIL_OPTION2, "detailOption", DUMMY_DETAIL_OPTION_INNER);
        ReflectionTestUtils.setField(DUMMY_CHECKED_DETAIL_OPTION3, "detailOption", DUMMY_DETAIL_OPTION_CONTRACT);

    }
}
