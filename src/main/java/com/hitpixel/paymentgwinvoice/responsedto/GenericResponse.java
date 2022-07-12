package com.hitpixel.paymentgwinvoice.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {
    private Boolean status;
    private String message;

    public static Map<String, Object> createFieldValidationError(Map<String, Object> errorDetails){
        Map<String, Object> errorMessage = new HashMap<>();
        errorMessage.put("error_code", 4000);
        errorMessage.put("error_message", "validation failed for request data. Please check below error details.");
        errorMessage.put("error_details", errorDetails);
        return errorMessage;
    }

    public static GenericResponse createErrorMessage(String message){
        return new GenericResponse(false, message);
    }
}
