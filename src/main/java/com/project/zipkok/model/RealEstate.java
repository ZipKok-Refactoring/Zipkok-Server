package com.project.zipkok.model;

import com.project.zipkok.common.enums.RealEstateType;
import com.project.zipkok.common.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RealEstate")
@Getter
@NoArgsConstructor
public class RealEstate {

    @Id
    @Column(name = "realestate_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long realEstateId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "deposit", nullable = false)
    private Long deposit;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "administrative_fee")
    private Integer administrativeFee;

    @Column(name = "detail")
    private String detail;

    @Column(name = "area_size")
    private Float areaSize;

    @Column(name = "pyeongsu")
    private Integer pyeongsu;

    @Column(name = "realestate_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RealEstateType realEstateType;

    @Column(name = "floor_num")
    private Integer floorNum;

    @Column(name = "status", nullable = false)
    private String status = "active";

    @Column(name = "user_user_id")
    private Long userId;

    @Column(name = "agent")
    private String agent;

    @Column(name = "detail_address")
    private String detailAddress;

    @OneToMany(mappedBy = "realEstate",orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RealEstateImage> realEstateImages = new ArrayList<>();

    @Builder
    public RealEstate(String address, Double latitude, Double longitude, TransactionType transactionType, Long deposit, Long price, RealEstateType realEstateType, Integer administrativeFee, String agent, String detail, Float areaSize, Integer pyeongsu, Integer floorNum, String imageUrl, String detailAddress, Long userId) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.transactionType = transactionType;
        this.deposit = deposit;
        this.price = price;
        this.realEstateType = realEstateType;
        this.administrativeFee = administrativeFee;
        this.agent = agent;
        this.detail = detail;
        this.areaSize = areaSize;
        this.pyeongsu = pyeongsu;
        this.floorNum = floorNum;
        this.imageUrl = imageUrl;
        this.detailAddress = detailAddress;
        this.userId = userId;
        this.status = "active";
    }
}
