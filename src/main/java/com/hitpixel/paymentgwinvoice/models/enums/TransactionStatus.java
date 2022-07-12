package com.hitpixel.paymentgwinvoice.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.Optional;

public enum TransactionStatus {

    APPROVED("approved"),
    DECLINED("declined"),
    REFUNDED("refunded");

    private final String transactionStatus;
    TransactionStatus(String transactionStatus){
        this.transactionStatus = transactionStatus;
    }
    public String getStatus(){
        return transactionStatus;
    }

    @Override
    public String toString() {
        return transactionStatus;
    }
    @JsonCreator
    public static TransactionStatus getTransactionStatusByStatus(String status){
        Optional<TransactionStatus> optionalstatus = Arrays.stream(TransactionStatus.values()).filter(status1 -> status1.transactionStatus.equals(status)).findFirst();
        return optionalstatus.orElse(null);
    }

}
