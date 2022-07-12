package com.hitpixel.paymentgwinvoice.services;

import com.hitpixel.paymentgwinvoice.enums.CrudStatus;
import com.hitpixel.paymentgwinvoice.models.Client;
import com.hitpixel.paymentgwinvoice.models.Invoice;
import com.hitpixel.paymentgwinvoice.models.TransactionRecord;
import com.hitpixel.paymentgwinvoice.models.enums.ClientStatus;
import com.hitpixel.paymentgwinvoice.models.enums.TransactionStatus;
import com.hitpixel.paymentgwinvoice.repository.TransactionRepository;
import com.hitpixel.paymentgwinvoice.requestdto.TxnCreateRequest;
import com.hitpixel.paymentgwinvoice.responsedto.TxnCreateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientService clientService;

    @Autowired
    private InvoiceService invoiceService;

    public TransactionRecord get(String orderId){
        return transactionRepository.findByOrderId(orderId);
    }

    public TxnCreateResponse send(TxnCreateRequest request){
        TransactionRecord transactionRecord = this.get(request.getOrderId());
        if (request.getStatus() == TransactionStatus.APPROVED) {
            if(transactionRecord != null)
                return new TxnCreateResponse(false, transactionRecord.getId(), request.getOrderId(), CrudStatus.FAILED.getStatus(), "could not approve already processed order, only you can request for refund if it is not billed and if approved txn.");
            return approvedTxn(request);
        } else if(request.getStatus() == TransactionStatus.DECLINED){
            if(transactionRecord != null)
                return new TxnCreateResponse(false, transactionRecord.getId(), request.getOrderId(), CrudStatus.FAILED.getStatus(), "could not decline already processed order.");
            return declinedTxn(request);
        }else if(request.getStatus() == TransactionStatus.REFUNDED){
            if(transactionRecord == null)
                return new TxnCreateResponse(false, null, request.getOrderId(), CrudStatus.FAILED.getStatus(), "could not find order.");
            if(!transactionRecord.getAmount().equals(request.getAmount()))
                return new TxnCreateResponse(false, transactionRecord.getId(), request.getOrderId(), CrudStatus.FAILED.getStatus(), "transaction/order amount doesn't match.");
            if(transactionRecord.getTransactionStatus().equals(TransactionStatus.DECLINED))
                return new TxnCreateResponse(false, transactionRecord.getId(), request.getOrderId(), CrudStatus.FAILED.getStatus(), "declined transaction cannot be refunded. order should be approved");
            if(transactionRecord.getTransactionStatus().equals(TransactionStatus.REFUNDED))
                return new TxnCreateResponse(false, transactionRecord.getId(), request.getOrderId(), CrudStatus.FAILED.getStatus(), "refunded transaction cannot be refunded. order should be approved");
            return refundTxn(request, transactionRecord);
        }
        return null;
    }

    private TxnCreateResponse approvedTxn(TxnCreateRequest request){
        TransactionRecord transactionRecord = new TransactionRecord(request);
        Client client = clientService.getByName(request.getClient());
        if(client == null || client.getStatus().equals(ClientStatus.DISABLED))
            return TxnCreateResponse.createErrorResponse("client doesn't available or not active");
        Invoice invoice = null;
        if(client.getCurrentInvoiceId() == null){
            invoice = new Invoice(client);
            invoice = invoiceService.add(invoice);
            client.setCurrentInvoiceId(invoice.getId());
        }else {
            invoice = invoiceService.get(client.getCurrentInvoiceId());
        }
        transactionRecord.setClient(client);
        transactionRecord = transactionRepository.save(transactionRecord);
        Double fees = clientService.calculateFeeForClient(client.getId(), Double.valueOf(request.getAmount()));
        invoice.setTotalAmount(invoice.getTotalAmount() + fees);
        invoice.setApprovedAmount(invoice.getApprovedAmount() + fees);
        invoice.setApprovedTransactions(invoice.getApprovedTransactions() + 1);
        invoice.setTotalTransactions(invoice.getTotalTransactions() + 1);
        invoice.getTransactions().add(transactionRecord);
        invoiceService.add(invoice);
        clientService.save(client);
        return new TxnCreateResponse(true, transactionRecord.getId(), request.getOrderId(), CrudStatus.CREATED.getStatus(), "transaction approved");
    }

    private TxnCreateResponse declinedTxn(TxnCreateRequest request){
        TransactionRecord transactionRecord = new TransactionRecord(request);
        Client client = clientService.getByName(request.getClient());
        if(client == null || client.getStatus().equals(ClientStatus.DISABLED))
            return TxnCreateResponse.createErrorResponse("client doesn't available or not active");
        Invoice invoice = null;
        if(client.getCurrentInvoiceId() == null){
            invoice = new Invoice(client);
            invoice = invoiceService.add(invoice);
            client.setCurrentInvoiceId(invoice.getId());
        }else {
            invoice = invoiceService.get(client.getCurrentInvoiceId());
        }
        transactionRecord.setClient(client);
        transactionRecord = transactionRepository.save(transactionRecord);
        invoice.setDeclinedTransactions(invoice.getDeclinedTransactions() + 1);
        invoice.setTotalTransactions(invoice.getTotalTransactions() + 1);
        invoice.getTransactions().add(transactionRecord);
        invoiceService.add(invoice);
        clientService.save(client);
        return new TxnCreateResponse(true, transactionRecord.getId(), request.getOrderId(), CrudStatus.CREATED.getStatus(), "transaction declined");
    }

    private TxnCreateResponse refundTxn(TxnCreateRequest request, TransactionRecord transactionRecord){
        Client client = clientService.getByName(request.getClient());
        if(client == null || client.getStatus().equals(ClientStatus.DISABLED))
            return TxnCreateResponse.createErrorResponse("client doesn't available or not active");
        Invoice invoice = null;
        if(client.getCurrentInvoiceId() == null){
            invoice = new Invoice(client);
            invoice = invoiceService.add(invoice);
            client.setCurrentInvoiceId(invoice.getId());
        }else {
            invoice = invoiceService.get(client.getCurrentInvoiceId());
        }
        Double fees = clientService.calculateFeeForClient(client.getId(), Double.valueOf(request.getAmount()));
        invoice.setRefundedAmount(invoice.getRefundedAmount() + fees);
        invoice.setTotalAmount(invoice.getTotalAmount() - invoice.getRefundedAmount());
        invoice.setTotalTransactions(invoice.getTotalTransactions() + 1);
        invoice.setRefundedTransactions(invoice.getRefundedTransactions() + 1);
        transactionRecord.setTransactionStatus(TransactionStatus.REFUNDED);
        transactionRecord = transactionRepository.save(transactionRecord);

        invoiceService.add(invoice);
        clientService.save(client);
        return new TxnCreateResponse(true, transactionRecord.getId(), request.getOrderId(), CrudStatus.CREATED.getStatus(), "refund success");
    }
}
