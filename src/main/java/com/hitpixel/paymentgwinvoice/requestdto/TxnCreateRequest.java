package com.hitpixel.paymentgwinvoice.requestdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hitpixel.paymentgwinvoice.models.enums.CardType;
import com.hitpixel.paymentgwinvoice.models.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxnCreateRequest {
    private String id;
    @NotBlank(message = "orderid must not be empty")
    @JsonProperty("orderid")
    private String orderId;
    @NotBlank(message = "datetime must not be empty")
    private String datetime;
    @NotBlank(message = "order name must not be empty")
    @JsonProperty("ordername")
    private String orderName;
    @NotBlank
    @Pattern(regexp="[0-9]{1,13}(\\\\.[0-9]*)?", message = "enter only numbers")
    private String amount;
    @NotBlank(message = "currency must not be empty")
    @Size(min = 3, max = 3)
    private String currency;
    @JsonProperty("cardtype")
    private CardType cardType;
    private TransactionStatus status;
    @NotBlank(message = "client must not be empty")
    private String client;
}
