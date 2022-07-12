package com.hitpixel.paymentgwinvoice.responsedto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hitpixel.paymentgwinvoice.models.Invoice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceAllCustom {
    @JsonProperty("client_id")
    private String clientId;
    private String client;
    private InvoiceCustom invoice;

    public InvoiceAllCustom(Invoice invoice){
        this.clientId = invoice.getClient().getId();
        this.client = invoice.getClient().getClient();
        this.invoice = new InvoiceCustom(invoice);
    }
}
