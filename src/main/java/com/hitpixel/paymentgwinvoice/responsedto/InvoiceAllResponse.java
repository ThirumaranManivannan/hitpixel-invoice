package com.hitpixel.paymentgwinvoice.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceAllResponse {
    private Boolean status;
    private String message;
    private List<InvoiceAllCustom> invoices;

}
