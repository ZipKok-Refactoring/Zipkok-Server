package com.project.zipkok.user.fixture.dto;

import com.project.zipkok.common.enums.Gender;
import com.project.zipkok.common.enums.OAuthProvider;
import com.project.zipkok.common.enums.RealEstateType;
import com.project.zipkok.common.enums.TransactionType;
import com.project.zipkok.dto.*;

import java.util.List;

public class UserDtoFixture {

    public static PostSignUpRequest createDefaultPostSignUpRequest() {

        return createCustomPostSignUpRequest("test", "test@test.com", "001212");
    }

    public static PostSignUpRequest createCustomPostSignUpRequest(String nickname, String email, String birthday) {
        return PostSignUpRequest.builder()
                .nickname(nickname)
                .oauthProvider(OAuthProvider.KAKAO.toString())
                .email(email)
                .gender(Gender.MALE.toString())
                .birthday(birthday)
                .build();
    }

    public static PatchOnBoardingRequest createDefaultPatchOnBoardingRequest() {

        return createCustomPatchOnBoardingRequest("test address", 100D, 100D);
    }

    public static PatchOnBoardingRequest createCustomPatchOnBoardingRequest(String address, double latitude, double longitude) {
        return PatchOnBoardingRequest.builder()
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .mdepositMin(100L)
                .mdepositMax(100L)
                .mpriceMin(100L)
                .mpriceMax(100L)
                .purchaseMin(100L)
                .purchaseMax(100L)
                .ydepositMin(100L)
                .ydepositMax(100L)
                .realEstateType(RealEstateType.APARTMENT.toString())
                .transactionType(TransactionType.MONTHLY.toString())
                .build();
    }


    public static GetMyPageResponse createDefaultGetMyPageResponse() {

        return createCustomGetMyPageResponse("test", "test image", "test address");
    }

    public static GetMyPageResponse createCustomGetMyPageResponse(String nickname, String imageUrl, String address) {
        return GetMyPageResponse.builder()
                .nickname(nickname)
                .imageUrl(imageUrl)
                .address(address)
                .realEstateType(RealEstateType.APARTMENT.toString())
                .transactionType(TransactionType.MONTHLY.toString())
                .priceMax(10000L)
                .depositMax(10000L)
                .priceMin(10000L)
                .depositMin(10000L)
                .build();
    }

    public static GetMyPageDetailResponse createDefaultGetMyPageDetailResponse() {
        return GetMyPageDetailResponse.builder()
                .nickname("test")
                .imageUrl("test image")
                .address("test address")
                .realEstateType(RealEstateType.APARTMENT.toString())
                .transactionType(TransactionType.MONTHLY.toString())
                .latitude(100D)
                .longitude(100D)
                .mdepositMin(100L)
                .mdepositMax(100L)
                .mpriceMin(100L)
                .mpriceMax(100L)
                .priceMin(100L)
                .priceMax(100L)
                .ydepositMin(100L)
                .ydepositMax(100L)
                .build();
    }

    public static GetKokOptionLoadResponse createDefaultGetKokOptionLoadResponse(List<String> highlights, List<com.project.zipkok.model.Option> outerOptions, List<com.project.zipkok.model.Option> innerOptions, List<com.project.zipkok.model.Option> contractOptions) {

        return GetKokOptionLoadResponse.builder()
                .highlights(highlights)
                .outerOptions(outerOptions.stream()
                        .map(GetKokOptionLoadResponse.Option::from)
                        .toList())
                .innerOptions(innerOptions.stream()
                        .map(GetKokOptionLoadResponse.Option::from)
                        .toList())
                .contractOptions(contractOptions.stream()
                        .map(GetKokOptionLoadResponse.Option::from)
                        .toList())
                .build();
    }

    public static PostUpdateKokOptionRequest createDefaultPostUpdateKokOptionRequest(List<String> highlights, List<com.project.zipkok.dto.PostUpdateKokOptionRequest.Option> outerOptions, List<com.project.zipkok.dto.PostUpdateKokOptionRequest.Option> innerOptions, List<com.project.zipkok.dto.PostUpdateKokOptionRequest.Option> contractOptions) {

        return PostUpdateKokOptionRequest.builder()
                .highlights(highlights)
                .outerOptions(outerOptions)
                .innerOptions(innerOptions)
                .contractOptions(contractOptions)
                .build();
    }
}
