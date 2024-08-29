package com.project.zipkok.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.zipkok.model.RealEstate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class GetRealEstateResponse {

    private Long realEstateId;

    private ImageInfo imageInfo;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class ImageInfo {
        private int imageNumber;
        private List<String> imageURL;
    }

    private String address;

    private String detailAddress;

    private String transactionType;

    private Long deposit;

    private Long price;

    private String detail;

    private Float areaSize;

    private Integer pyeongsu;

    private String realEstateType;

    private Integer floorNum;

    private Integer administrativeFee;

    private Double latitude;

    private Double longitude;

    @JsonProperty("isZimmed")
    private boolean isZimmed;

    @JsonProperty("isKokked")
    private boolean isKokked;

    private List<RealEstateBriefInfo> neighborRealEstates;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class RealEstateBriefInfo {
        private Long realEstateId;
        private String imageUrl;
        private String address;
        private Long deposit;
        private Long price;

        public static RealEstateBriefInfo from(RealEstate realEstate) {
            return RealEstateBriefInfo.builder()
                    .realEstateId(realEstate.getRealEstateId())
                    .imageUrl(realEstate.getImageUrl())
                    .address(realEstate.getAddress())
                    .deposit(realEstate.getDeposit())
                    .price(realEstate.getPrice())
                    .build();
        }
    }

    public boolean getIsKokked() {
        return isKokked;
    }

    public boolean getIsZimmed() {
        return isZimmed;
    }

    public static GetRealEstateResponse of(RealEstate realEstate, boolean isZimmed, boolean isKokked, ImageInfo imageInfo, List<RealEstateBriefInfo> neighborRealEstates) {

        return GetRealEstateResponse.builder()
                .realEstateId(realEstate.getRealEstateId())
                .address(realEstate.getAddress())
                .detailAddress(realEstate.getDetailAddress())
                .transactionType(realEstate.getTransactionType().name())
                .deposit(realEstate.getDeposit())
                .price(realEstate.getPrice())
                .detail(realEstate.getDetail())
                .areaSize(realEstate.getAreaSize())
                .pyeongsu(realEstate.getPyeongsu())
                .realEstateType(realEstate.getRealEstateType().name())
                .floorNum(realEstate.getFloorNum())
                .administrativeFee(realEstate.getAdministrativeFee())
                .latitude(realEstate.getLatitude())
                .longitude(realEstate.getLongitude())
                .build();
    }
}
