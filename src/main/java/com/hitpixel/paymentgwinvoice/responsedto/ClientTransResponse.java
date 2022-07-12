package com.hitpixel.paymentgwinvoice.responsedto;

import com.hitpixel.paymentgwinvoice.models.TransactionRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientTransResponse {
    private Boolean status;
    private String message;
    private String client;
    private List<TransactionRecord> transactions;

    public ClientTransResponse(Boolean status, String message){
        this.status = status;
        this.message = message == null ? "unknown" : message;
    }
}
