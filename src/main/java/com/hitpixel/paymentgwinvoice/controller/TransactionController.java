package com.hitpixel.paymentgwinvoice.controller;

import com.hitpixel.paymentgwinvoice.requestdto.TxnCreateRequest;
import com.hitpixel.paymentgwinvoice.responsedto.GenericResponse;
import com.hitpixel.paymentgwinvoice.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/txn")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/send")
    public ResponseEntity<?> add(@RequestBody @Valid TxnCreateRequest txnCreateRequest){
        if(!validate(txnCreateRequest).isEmpty())
            return new ResponseEntity<>(GenericResponse.createFieldValidationError(validate(txnCreateRequest)), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(transactionService.send(txnCreateRequest), HttpStatus.OK);
    }













public Map<String, Object> validate(TxnCreateRequest request){
        Map<String, Object> errorDetails = new HashMap<>();
        if(request.getCardType() == null)
            errorDetails.put("cardtype", "card type is mandatory or should be visa or master");
        if(request.getStatus() == null)
            errorDetails.put("status", "status is mandatory or should be approved, declined, refunded");
        return errorDetails;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error_code", 4000);
        errorDetails.put("error_message", "validation failed for request data. Please check below error details.");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        errorDetails.put("error_details", errors);
        return errorDetails;
    }
}
