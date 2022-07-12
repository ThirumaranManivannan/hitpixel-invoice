package com.hitpixel.paymentgwinvoice.services;

import com.hitpixel.paymentgwinvoice.models.Client;
import com.hitpixel.paymentgwinvoice.models.Invoice;
import com.hitpixel.paymentgwinvoice.repository.InvoiceRepository;
import com.hitpixel.paymentgwinvoice.responsedto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmailService emailService;

    public Invoice add(Invoice invoice){
        return invoiceRepository.save(invoice);
    }
    public Invoice get(String invoiceId){
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(invoiceId);
        return optionalInvoice.orElse(null);
    }

    public InvoiceResponse generateInvoice(String clientId){
        Optional<Client> optionalClient = clientService.get(clientId);
        if(optionalClient.isEmpty())
            return new InvoiceResponse(false, "couldn't find client", null);
        Client client = optionalClient.get();
        String currInvoiceId = client.getCurrentInvoiceId();
        if(currInvoiceId == null)
            return new InvoiceResponse(false, "please make atleast one transaction", null);
        Invoice invoice = this.get(currInvoiceId);
        invoice.setGenerated(true);
        invoice = this.add(invoice);
        client.setCurrentInvoiceId(null);
        clientService.save(client);
        emailService.send(client.getClient(), client.getEmail(), invoice);
        return new InvoiceResponse(true, "invoice generated", new InvoiceCustom(invoice));
    }

    public InvoiceAllResponse generateForALl(){
        List<Invoice> invoicesList = invoiceRepository.findByIsGenerated(false);
        List<InvoiceAllCustom> invoices = new ArrayList<>();

        for(Invoice invoice : invoicesList){
            invoice.setGenerated(true);
            add(invoice);
            invoices.add(new InvoiceAllCustom(invoice));
        }
        if(invoices.isEmpty())
            return new InvoiceAllResponse(false, "please make atleast one transaction", null);
        return new InvoiceAllResponse(true, "invoices generated", invoices);
    }

    public ClientTransResponse getTransactions(String clientId){
        Optional<Client> optionalClient = clientService.get(clientId);
        if(optionalClient.isEmpty())
            return new ClientTransResponse(false, "client not available");
        Client client = optionalClient.get();
        if(client.getCurrentInvoiceId() == null || client.getCurrentInvoiceId().isEmpty())
            return new ClientTransResponse(false, "no active transactions found. if txns are invoiced then it won't be available here.");
        Invoice invoice = this.get(client.getCurrentInvoiceId());
        return new ClientTransResponse(true, "success", client.getClient(), invoice.getTransactions());
    }
}
