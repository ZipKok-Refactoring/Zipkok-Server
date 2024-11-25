package com.project.zipkok.model;

import com.project.zipkok.dto.PatchOnBoardingRequest;
import com.project.zipkok.dto.PutUpdateMyInfoRequest;
import jakarta.persistence.*;
import jakarta.transaction.Transaction;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.transaction.Transaction;
import lombok.*;

@Entity
@Table(name = "TransactionPriceConfig")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class TransactionPriceConfig {

    @Id
    @Column(name = "transaction_config_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionConfigId;

    @Column(name = "mprice_min")
    private long mPriceMin;

    @Column(name = "mprice_max")
    private long mPriceMax;

    @Column(name = "mdeposit_min")
    private long mDepositMin;

    @Column(name = "mdeposit_max")
    private long mDepositMax;

    @Column(name = "ydeposit_min")
    private long yDepositMin;

    @Column(name = "ydeposit_max")
    private long yDepositMax;

    @Column(name = "purchase_min")
    private long purchaseMin;

    @Column(name = "purchase_max")
    private long purchaseMax;

    @Column(name = "status", nullable = false)
    private String status = "active";

    @OneToOne(mappedBy = "transactionPriceConfig", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;


    public TransactionPriceConfig(User user) {
        this.user = user;
    }

    public void setTransactionPriceConfig(PatchOnBoardingRequest patchOnBoardingRequest) {
        this.mPriceMin = patchOnBoardingRequest.getMpriceMin();
        this.mPriceMax = patchOnBoardingRequest.getMpriceMax();
        this.mDepositMin = patchOnBoardingRequest.getMdepositMin();
        this.mDepositMax = patchOnBoardingRequest.getMdepositMax();
        this.yDepositMin = patchOnBoardingRequest.getYdepositMin();
        this.yDepositMax = patchOnBoardingRequest.getYdepositMax();
        this.purchaseMin = patchOnBoardingRequest.getPurchaseMin();
        this.purchaseMax = patchOnBoardingRequest.getPurchaseMax();
    }

    public void setTransactionPriceConfig(PutUpdateMyInfoRequest putUpdateMyInfoRequest) {
        this.mPriceMin = putUpdateMyInfoRequest.getMpriceMin();
        this.mPriceMax = putUpdateMyInfoRequest.getMpriceMax();
        this.mDepositMin = putUpdateMyInfoRequest.getMdepositMin();
        this.mDepositMax = putUpdateMyInfoRequest.getMdepositMax();
        this.yDepositMin = putUpdateMyInfoRequest.getYdepositMin();
        this.yDepositMax = putUpdateMyInfoRequest.getYdepositMax();
        this.purchaseMin = putUpdateMyInfoRequest.getPurchaseMin();
        this.purchaseMax = putUpdateMyInfoRequest.getPurchaseMax();
    }

    @Builder
    public TransactionPriceConfig(Long mPriceMin, Long mPriceMax, Long mDepositMin, Long mDepositMax, Long yDepositMin, Long yDepositMax, Long purchaseMin, Long purchaseMax) {
        this.mPriceMin = mPriceMin;
        this.mPriceMax = mPriceMax;
        this.mDepositMin = mDepositMin;
        this.mDepositMax = mDepositMax;
        this.yDepositMin = yDepositMin;
        this.yDepositMax = yDepositMax;
        this.purchaseMin = purchaseMin;
        this.purchaseMax = purchaseMax;
        this.status = "active";
    }
}
