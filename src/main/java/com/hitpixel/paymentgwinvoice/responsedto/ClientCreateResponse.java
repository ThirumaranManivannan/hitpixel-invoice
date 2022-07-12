package com.hitpixel.paymentgwinvoice.responsedto;

import com.hitpixel.paymentgwinvoice.enums.CrudStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreateResponse {
    private Boolean status;
    private String clientId;
    private CrudStatus message;
}
