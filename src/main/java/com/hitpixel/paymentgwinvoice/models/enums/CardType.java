package com.hitpixel.paymentgwinvoice.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.Optional;

public enum CardType {
    VISA("visa"),
    MASTER_CARD("master");

    private final String cardType;

    CardType(String cardType){
        this.cardType = cardType;
    }
    public String getCardType(){
        return cardType;
    }

    @Override
    public String toString() {
        return cardType;
    }
    @JsonCreator
    public static CardType getCardTypeByCardType(String cardType){
        Optional<CardType>  optionalCardType = Arrays.stream(CardType.values()).filter(cardType1 -> cardType1.cardType.equals(cardType)).findFirst();
        return optionalCardType.orElse(null);
    }
}
