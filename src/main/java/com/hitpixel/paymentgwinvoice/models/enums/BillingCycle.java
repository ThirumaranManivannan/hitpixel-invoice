package com.hitpixel.paymentgwinvoice.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.Optional;

public enum BillingCycle {
    DAILY("daily"),
    WEEKLY("weekly"),
    MONTHLY("monthly");

    private final String billCycle;
    BillingCycle(String billCycle){
        this.billCycle = billCycle;
    }
    public String getBillCycle(){
        return billCycle;
    }
    @Override
    public String toString(){
        return billCycle;
    }
    @JsonCreator
    public static BillingCycle getBillingCycleByCycle(String cycle){
        Optional<BillingCycle> optionalBillingCycle = Arrays.stream(BillingCycle.values()).filter(billingCycle -> billingCycle.billCycle.equals(cycle)).findFirst();
        return optionalBillingCycle.orElse(null);
    }
}
