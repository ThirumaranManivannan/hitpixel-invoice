package com.hitpixel.paymentgwinvoice.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.Optional;

public enum FeeType {
    FLAT_FEE("flat-fee"),
    PERCENT("percentage");

    private final String feeType;
    FeeType(String feeType){
        this.feeType = feeType;
    }

    public String getFeeType(){
        return feeType;
    }
    @Override
    public String toString(){
        return feeType;
    }

    @JsonCreator
    public static FeeType getFeeTypeBytype(String type){
        Optional<FeeType> optionalFeeType = Arrays.stream(FeeType.values()).filter(feeType1 -> feeType1.feeType.equals(type)).findFirst();
        return optionalFeeType.orElse(null);
    }
}
