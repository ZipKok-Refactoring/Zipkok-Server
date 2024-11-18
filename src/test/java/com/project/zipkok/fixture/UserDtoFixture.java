package com.project.zipkok.fixture;

import com.project.zipkok.common.enums.Gender;
import com.project.zipkok.common.enums.OAuthProvider;
import com.project.zipkok.common.enums.RealEstateType;
import com.project.zipkok.common.enums.TransactionType;
import com.project.zipkok.dto.PatchOnBoardingRequest;
import com.project.zipkok.dto.PostSignUpRequest;

public class UserDtoFixture {

    public static PostSignUpRequest createDefaultPostSignUpRequest() {
        return PostSignUpRequest.builder()
                .nickname("test")
                .oauthProvider(OAuthProvider.KAKAO)
                .email("test@test.com")
                .gender(Gender.MALE)
                .birthday("001212")
                .build();
    }

    public static PatchOnBoardingRequest createDefaultPatchOnBoardingRequest() {
        return PatchOnBoardingRequest.builder()
                .address("test address")
                .latitude(100D)
                .longitude(100D)
                .mdepositMin(100L)
                .mdepositMax(100L)
                .mpriceMin(100L)
                .mpriceMax(100L)
                .purchaseMin(100L)
                .purchaseMax(100L)
                .ydepositMin(100L)
                .ydepositMax(100L)
                .realEstateType(RealEstateType.APARTMENT)
                .transactionType(TransactionType.MONTHLY)
                .build();
    }
}
