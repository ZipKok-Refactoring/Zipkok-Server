package com.project.zipkok.fixture;

import com.project.zipkok.model.DesireResidence;
import com.project.zipkok.model.User;

public class DesireResidenceFixture {

    public static DesireResidence createDefaultDesireResidence(User user) {
        return DesireResidence.builder()
                .user(user)
                .address("서울시 강남구")
                .latitude(100D)
                .longitude(100D)
                .status("active")
                .build();
    }

    public static DesireResidence createCustomDesireResidence(User user, String address, Double latitude, Double longitude) {
        return DesireResidence.builder()
                .user(user)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .status("active")
                .build();
    }
}
