package com.hitpixel.paymentgwinvoice.responsedto;

import com.hitpixel.paymentgwinvoice.models.Invoice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class InvoiceResponse {
    private Boolean status;
    private String message;
    private InvoiceCustom invoice;
}
