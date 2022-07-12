package com.hitpixel.paymentgwinvoice.requestdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hitpixel.paymentgwinvoice.annotations.EnumValidator;
import com.hitpixel.paymentgwinvoice.models.enums.BillingCycle;
import com.hitpixel.paymentgwinvoice.models.enums.ClientStatus;
import com.hitpixel.paymentgwinvoice.models.enums.FeeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreateRequest {
    private String id;
    @NotBlank(message = "client name is mandatory, for eg. pizza store")
    private String client;
    private ClientStatus status;
    @JsonProperty("billing-interval")
    private BillingCycle billingInterval;
    @Email(message = "Please enter valid email address, for eg. s@c.com")
    private String email;
    @JsonProperty("fees-type")
    private FeeType feesType;
    @NotBlank(message = "fees mandatory, for eg. 1.00")
    private String fees;

}
