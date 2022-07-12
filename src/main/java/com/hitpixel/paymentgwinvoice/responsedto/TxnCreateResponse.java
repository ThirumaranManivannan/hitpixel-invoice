package com.hitpixel.paymentgwinvoice.responsedto;

import com.hitpixel.paymentgwinvoice.enums.CrudStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxnCreateResponse {
    private Boolean status;
    private String invoiceTxnId;
    private String orderId;
    private String message;
    private String description;

    public static TxnCreateResponse createErrorResponse(String description){
        return new TxnCreateResponse(false, null, null, CrudStatus.FAILED.getStatus(), description);
    }
}
