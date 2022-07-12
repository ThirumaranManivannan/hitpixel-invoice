package com.hitpixel.paymentgwinvoice.responsedto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hitpixel.paymentgwinvoice.models.Client;
import com.hitpixel.paymentgwinvoice.models.TransactionRecord;
import com.hitpixel.paymentgwinvoice.models.enums.BillingCycle;
import com.hitpixel.paymentgwinvoice.models.enums.ClientStatus;
import com.hitpixel.paymentgwinvoice.models.enums.FeeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {
    private String id;
    private String client;
    private String status;
    @JsonProperty("billing-interval")
    private String billingInterval;
    private String email;
    @JsonProperty("fees-type")
    private String feesType;
    private String fees;

    public ClientResponse(Client client){
        this.id = client.getId();
        this.client = client.getClient();
        this.status = client.getStatus().getStatus();
        this.billingInterval = client.getBillingInterval().getBillCycle();
        this.email = client.getEmail();
        this.feesType = client.getFeesType().getFeeType();
        this.fees = client.getFees();
    }
}
