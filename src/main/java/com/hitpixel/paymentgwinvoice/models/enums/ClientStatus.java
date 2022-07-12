package com.hitpixel.paymentgwinvoice.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.Optional;

public enum ClientStatus {
    ACTIVE("active"),
    DISABLED("disabled");

    private final String status;

    ClientStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
    @JsonCreator
    public static ClientStatus getClientStatusByStatus(String status){
        Optional<ClientStatus> optionalClientStatus = Arrays.stream(ClientStatus.values()).filter(clientStatus -> clientStatus.status.equals(status)).findFirst();
        return optionalClientStatus.orElse(null);
    }
    @Override
    public String toString() {
        return status;
    }
}
