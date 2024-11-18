package com.project.zipkok.fixture;

import com.project.zipkok.common.enums.RealEstateType;
import com.project.zipkok.common.enums.TransactionType;
import com.project.zipkok.model.RealEstate;

public class RealEstateFixture {

    public static RealEstate createDefaultRealEstate(String address) {
        return RealEstate.builder()
                .address(address)
                .latitude(100)
                .longitude(200)
                .transactionType(TransactionType.MONTHLY)
                .deposit(1000)
                .price(10000)
                .realEstateType(RealEstateType.APARTMENT)
                .status("active")
                .build();
    }
}
