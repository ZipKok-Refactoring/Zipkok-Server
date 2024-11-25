package com.project.zipkok.user.fixture.domain;

import com.project.zipkok.common.enums.RealEstateType;
import com.project.zipkok.common.enums.TransactionType;
import com.project.zipkok.model.RealEstate;

public class RealEstateFixture {

    public static RealEstate createDefaultRealEstate(String address) {
        return RealEstate.builder()
                .address(address)
                .latitude(100.0)
                .longitude(200.0)
                .transactionType(TransactionType.MONTHLY)
                .deposit(1000L)
                .price(10000L)
                .realEstateType(RealEstateType.APARTMENT)
                //.status("active")
                .build();
    }
}
