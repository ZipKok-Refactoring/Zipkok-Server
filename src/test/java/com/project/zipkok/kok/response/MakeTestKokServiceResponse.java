package com.project.zipkok.kok.response;

import com.project.zipkok.common.enums.*;
import com.project.zipkok.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MakeTestKokServiceResponse {

    public static Kok makeTestKok(
            Long reId, String imageurl, String address, String daddress, String agent, TransactionType transType, RealEstateType realType, Long deposit, int price,
            Long kokId, String url,
            Long userId,
            String optionName, boolean optionVisible, int optionOrderNum,
            String detailOptionName, boolean detailOptionVisible,
            String kokReview
    ){

        User dummyUser = User.builder().build();
        Kok dummyKok = Kok.builder().build();
        RealEstate realEstate = getRealEstate(reId,imageurl,address,daddress,agent,transType,realType,deposit,price);

        Highlight highlight = new Highlight(dummyUser, "test");
        CheckedHighlight checkedHighlight = new CheckedHighlight(dummyKok,highlight);
        FurnitureOption furnitureOption = new FurnitureOption("test","test");
        CheckedFurniture checkedFurniture = new CheckedFurniture(dummyKok,furnitureOption);
        Impression impression = new Impression("test");
        CheckedImpression checkedImpression = new CheckedImpression(dummyKok, impression);

        Option option = new Option(optionName, optionVisible, optionOrderNum, OptionCategory.OUTER, dummyUser);
        CheckedOption checkedOption = new CheckedOption(dummyKok, option);
        Option option2 = new Option(optionName, optionVisible, optionOrderNum, OptionCategory.INNER, dummyUser);
        CheckedOption checkedOption2 = new CheckedOption(dummyKok, option2);
        Option option3 = new Option(optionName, optionVisible, optionOrderNum, OptionCategory.CONTRACT, dummyUser);
        CheckedOption checkedOption3 = new CheckedOption(dummyKok, option3);

        DetailOption detailOption = new DetailOption(detailOptionName, detailOptionVisible, option);
        DetailOption detailOption2 = new DetailOption(detailOptionName, detailOptionVisible, option2);
        DetailOption detailOption3 = new DetailOption(detailOptionName, detailOptionVisible, option3);
        CheckedDetailOption checkedDetailOption = new CheckedDetailOption(dummyKok, detailOption);
        CheckedDetailOption checkedDetailOption2 = new CheckedDetailOption(dummyKok, detailOption2);
        CheckedDetailOption checkedDetailOption3 = new CheckedDetailOption(dummyKok, detailOption3);

        Star star = new Star(5,5,5, 5,dummyKok);

        User user = User.builder()
                .userId(userId)
                .zims(Set.of(
                        new Zim(realEstate, dummyUser)
                ))
                .highlights(Set.of(highlight))
                .options(Set.of(option, option2, option3))
                .impressions(Set.of(impression))
                .build();

        List<CheckedHighlight> checkedHighlights = new ArrayList<>();
        checkedHighlights.add(checkedHighlight);
        List<CheckedFurniture> checkedFurnitures = new ArrayList<>();
        checkedFurnitures.add(checkedFurniture);
        List<CheckedImpression> checkedImpressions = new ArrayList<>();
        checkedImpressions.add(checkedImpression);
        Set<CheckedOption> checkedOptions = new HashSet<>();
        checkedOptions.add(checkedOption);
        checkedOptions.add(checkedOption2);
        checkedOptions.add(checkedOption3);
        Set<CheckedDetailOption> checkedDetailOptions = new HashSet<>();
        checkedDetailOptions.add(checkedDetailOption);
        checkedDetailOptions.add(checkedDetailOption2);
        checkedDetailOptions.add(checkedDetailOption3);

        List<KokImage> kokImages = new ArrayList<>();
        kokImages.add(KokImage.builder().imageUrl(url).category(OptionCategory.CONTRACT.getDescription()).build());

        return Kok.builder()
                .kokId(kokId)
                .realEstate(realEstate)
                .user(user)
                .checkedHighlights(checkedHighlights)
                .checkedFurnitures(checkedFurnitures)
                .checkedImpressions(checkedImpressions)
                .checkedOptions(checkedOptions)
                .checkedDetailOptions(checkedDetailOptions)
                .kokImages(kokImages)
                .star(star)
                .review(kokReview)
                .direction("test")
                .build();
    }

    private static RealEstate getRealEstate(Long reId, String imageurl, String address, String daddress, String agent, TransactionType transType, RealEstateType realType, Long deposit, int price){
        RealEstate dummyRealEstate = RealEstate.builder().build();
        RealEstateImage realEstateImage = new RealEstateImage(imageurl, dummyRealEstate);
        return RealEstate.builder()
                .realEstateId(reId)
                .realEstateImages(List.of(realEstateImage))
                .address(address)
                .detailAddress(daddress)
                .agent(agent)
                .transactionType(transType)
                .realEstateType(realType)
                .deposit(deposit)
                .price(price)
                .status("active")
                .build();
    }

    public static RealEstate getRealEstateWithoutImage(Long reId, String address, String daddress, String agent, TransactionType transType, RealEstateType realType, Long deposit, int price){
        return RealEstate.builder()
                .realEstateId(reId)
                .address(address)
                .detailAddress(daddress)
                .agent(agent)
                .transactionType(transType)
                .realEstateType(realType)
                .deposit(deposit)
                .price(price)
                .status("active")
                .build();
    }

    public static User getUser(Long userId){
        return User.builder()
                .userId(userId)
                .birthday("test")
                .email("test")
                .oAuthProvider(OAuthProvider.GOOGLE)
                .nickname("test")
                .gender(Gender.MALE)
                .role(Role.USER)
                .status("active")
                .build();
    }
}
