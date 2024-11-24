package com.project.zipkok.user.fixture;

import com.project.zipkok.model.Kok;
import com.project.zipkok.model.RealEstate;
import com.project.zipkok.model.User;

public class KokFixture {

    public static Kok createKok(RealEstate realEstate, User user, String direction, String review) {
        // return new Kok(realEstate, review, user);
        return Kok.builder()
                .direction(direction)
                .review(review)
                .realEstate(realEstate)
                .user(user)
                .build();
    }
}
