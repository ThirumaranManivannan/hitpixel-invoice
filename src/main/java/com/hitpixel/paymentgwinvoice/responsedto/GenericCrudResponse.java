package com.hitpixel.paymentgwinvoice.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericCrudResponse {
    private String id;
    private Boolean status;
    private String message;
}
