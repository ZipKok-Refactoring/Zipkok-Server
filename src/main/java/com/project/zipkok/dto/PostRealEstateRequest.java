package com.project.zipkok.dto;

import com.project.zipkok.common.enums.RealEstateType;
import com.project.zipkok.common.enums.TransactionType;
import com.project.zipkok.common.enums.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostRealEstateRequest {

    @NotBlank
    @Size(max = 30)
    private String realEstateName;

    @NotNull
    @ValidEnum(enumClass = TransactionType.class)
    private String transactionType;

    @NotNull
    @ValidEnum(enumClass = RealEstateType.class)
    private String realEstateType;

    private Long deposit;

    private Long price;

    @NotNull
    private Integer administrativeFee;

    @NotBlank
    private String address;

    private String detailAddress;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private Integer pyeongsu;

    private Integer floorNum;
}
