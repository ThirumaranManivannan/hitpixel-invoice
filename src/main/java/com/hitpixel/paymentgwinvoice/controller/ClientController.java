package com.hitpixel.paymentgwinvoice.controller;

import com.hitpixel.paymentgwinvoice.enums.CrudStatus;
import com.hitpixel.paymentgwinvoice.models.Client;
import com.hitpixel.paymentgwinvoice.requestdto.ClientCreateRequest;
import com.hitpixel.paymentgwinvoice.responsedto.ClientCreateResponse;
import com.hitpixel.paymentgwinvoice.responsedto.ClientResponse;
import com.hitpixel.paymentgwinvoice.responsedto.GenericResponse;
import com.hitpixel.paymentgwinvoice.services.ClientService;
import com.hitpixel.paymentgwinvoice.services.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/client")
@Slf4j
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody @Valid ClientCreateRequest clientCreateRequest){
        if(!this.validate(clientCreateRequest).isEmpty())
            return new ResponseEntity<>(GenericResponse.createFieldValidationError(this.validate(clientCreateRequest)), HttpStatus.BAD_REQUEST);
        ClientCreateResponse response = clientService.create(clientCreateRequest);
        if(!response.getStatus())
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/update")
     public ResponseEntity<?> update(@RequestBody @Valid ClientCreateRequest clientCreateRequest){

        if(clientCreateRequest.getId() == null || clientCreateRequest.getId().isEmpty()){
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("id", "id should not be empty.");
            return new ResponseEntity<>(GenericResponse.createFieldValidationError(errorDetails), HttpStatus.BAD_REQUEST);
        }

        if(!this.validate(clientCreateRequest).isEmpty())
            return new ResponseEntity<>(GenericResponse.createFieldValidationError(this.validate(clientCreateRequest)), HttpStatus.BAD_REQUEST);
        ClientCreateResponse response = clientService.create(clientCreateRequest);
        if(!response.getStatus())
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage(CrudStatus.UPDATED);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "id") String sortBy){
        List<ClientResponse> clients = clientService.getAll(pageNo, pageSize, sortBy);
        if(clients != null)
            return new ResponseEntity<>(clients, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get/{clientId}")
    public ResponseEntity<?> getClient(@PathVariable @NotBlank String clientId){
        Optional<Client> optionalClient = clientService.get(clientId);
        if(optionalClient.isPresent())
            return new ResponseEntity<>(new ClientResponse(optionalClient.get()), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<?> deleteById(@PathVariable @NotBlank String clientId){
        GenericResponse response = clientService.delete(clientId);
        if(response.getStatus())
            return new ResponseEntity<>(response, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{clientId}/transactions")
    public ResponseEntity<?> getTrans(@PathVariable String clientId){
        if(clientId == null || clientId.isEmpty()){
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("clientId", "client id should not be empty.");
            return new ResponseEntity<>(GenericResponse.createFieldValidationError(errorDetails), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(invoiceService.getTransactions(clientId),HttpStatus.OK);
    }























    public Map<String, Object> validate(ClientCreateRequest request){
        Map<String, Object> errorDetails = new HashMap<>();
        if(request.getFeesType() == null)
            errorDetails.put("fee-type", "fee type is mandatory or should be flat-fee or percentage");
        if(request.getStatus() == null)
            errorDetails.put("status", "status is mandatory or should be active or disabled");
        if(request.getBillingInterval() == null)
            errorDetails.put("billing-interval", "billing-interval is mandatory or should be daily, weekly or monthly");
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
