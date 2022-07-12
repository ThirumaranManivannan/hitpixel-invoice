package com.hitpixel.paymentgwinvoice.controller;

import com.hitpixel.paymentgwinvoice.models.Invoice;
import com.hitpixel.paymentgwinvoice.responsedto.GenericResponse;
import com.hitpixel.paymentgwinvoice.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/generate/{clientId}")
    public ResponseEntity<?> generate(@PathVariable String clientId){
        if(clientId == null || clientId.isEmpty())
            return new ResponseEntity<>(new GenericResponse(false, "clientId cannot be empty"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(invoiceService.generateInvoice(clientId), HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateAll(){
        return new ResponseEntity<>(invoiceService.generateForALl(), HttpStatus.OK);
    }

    @GetMapping("/get/{invoiceId}")
    public ResponseEntity<?> get(@PathVariable String invoiceId){
        if(invoiceId == null || invoiceId.isEmpty()){
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("clientId", "client id should not be empty.");
            return new ResponseEntity<>(GenericResponse.createFieldValidationError(errorDetails), HttpStatus.BAD_REQUEST);
        }
        Invoice invoice = invoiceService.get(invoiceId);
        if(invoice == null){
            return new ResponseEntity<>(new GenericResponse(false, "no invoice found"), HttpStatus.OK);
        }

        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }
}
