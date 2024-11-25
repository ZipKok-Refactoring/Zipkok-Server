package com.project.zipkok.user.fixture.domain;

import com.project.zipkok.model.RealEstate;
import com.project.zipkok.model.User;
import com.project.zipkok.model.Zim;

public class ZimFixture {

    public static Zim createZim(User user, RealEstate realEstate) {
        return new Zim(realEstate, user);
    }
}
