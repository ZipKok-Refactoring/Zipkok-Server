package com.project.zipkok.kok.response;

import com.project.zipkok.common.enums.OptionCategory;
import com.project.zipkok.model.*;

public class MakeTestKokRepositoryResponse {

    public static Kok makeTestKok(User user, RealEstate realEstate){
        Star star = Star.builder()
                .starId(1L)
                .facilityStar(5)
                .infraStar(5)
                .structureStar(5)
                .vibeStar(5)
                .build();

        return Kok.builder()
                .kokId(1L)
                .direction("test")
                .review("test")
                .user(user)
                .realEstate(realEstate)
                .star(star)
                .build();
    }

    public static Option makeTestOption(User user ){
        return Option.builder()
                .optionId(1L)
                .name("test")
                .isVisible(true)
                .orderNum(1)
                .category(OptionCategory.OUTER)
                .user(user)
                .build();
    }

    public static CheckedOption makeTestCheckedOption(Kok kok, Option option){
        return CheckedOption.builder()
                .checkedOptionId(1L)
                .kok(kok)
                .option(option)
                .build();
    }

    public static CheckedDetailOption makeTestCheckedDetailOption(Kok kok, DetailOption detailOption){
        return CheckedDetailOption.builder()
                .checkedDetailOptionId(1L)
                .kok(kok)
                .detailOption(detailOption)
                .build();
    }

    public static DetailOption makeTestDetailOption(Option option){
        return new DetailOption("name",true, option);
    }

    public static Impression makeTestImpression(){
        return Impression.builder()
                .impressionId(1L)
                .impressionTitle("test")
                .build();
    }

    public static CheckedImpression makeTestCheckedImpression(Kok kok, Impression impression){
        return CheckedImpression.builder()
                .checkedImpressionId(1L)
                .kok(kok)
                .impression(impression)
                .build();
    }

    public static Zim makeTestZim(RealEstate realEstate, User user){
        return new Zim(realEstate, user);
    }
}
