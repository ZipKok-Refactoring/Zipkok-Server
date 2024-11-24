package com.project.zipkok.user.fixture;

import com.project.zipkok.model.TransactionPriceConfig;
import com.project.zipkok.model.User;

public class TransactionPriceConfigFixture {
    public static TransactionPriceConfig createDefaultTransactionPriceConfig(User user) {
        return TransactionPriceConfig.builder()
                .user(user)
                .mPriceMin(100000L)
                .mPriceMax(500000L)
                .mDepositMin(100000L)
                .mDepositMax(300000L)
                .yDepositMin(200000L)
                .yDepositMax(600000L)
                .purchaseMin(50000000L)
                .purchaseMax(100000000L)
                .status("active")
                .build();
    }

    // 커스텀 TransactionPriceConfig 생성
    public static TransactionPriceConfig createCustomTransactionPriceConfig(User user, long mPriceMin, long mPriceMax, long mDepositMin, long mDepositMax, long yDepositMin, long yDepositMax, long purchaseMin, long purchaseMax) {
        return TransactionPriceConfig.builder()
                .user(user)
                .mPriceMin(mPriceMin)
                .mPriceMax(mPriceMax)
                .mDepositMin(mDepositMin)
                .mDepositMax(mDepositMax)
                .yDepositMin(yDepositMin)
                .yDepositMax(yDepositMax)
                .purchaseMin(purchaseMin)
                .purchaseMax(purchaseMax)
                .status("active")
                .build();
    }
}
