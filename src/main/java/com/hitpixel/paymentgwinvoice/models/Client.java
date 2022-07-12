package com.hitpixel.paymentgwinvoice.models;

import com.hitpixel.paymentgwinvoice.models.enums.BillingCycle;
import com.hitpixel.paymentgwinvoice.models.enums.ClientStatus;
import com.hitpixel.paymentgwinvoice.models.enums.FeeType;
import com.hitpixel.paymentgwinvoice.requestdto.ClientCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@ToString
public class Client {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String client;
    @Enumerated(EnumType.STRING)
    @Column(name = "client_status")
    private ClientStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name = "billing_interval")
    private BillingCycle billingInterval;
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "fees_type")
    private FeeType feesType;
    private String fees;
    @OneToMany
    @ToString.Exclude
    private List<TransactionRecord> transactions;
    @OneToMany
    @ToString.Exclude
    private List<Invoice> invoices;
    private String currentInvoiceId;

    public String getCurrentInvoiceId() {
        return currentInvoiceId;
    }

    public void setCurrentInvoiceId(String currentInvoiceId) {
        this.currentInvoiceId = currentInvoiceId;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public BillingCycle getBillingInterval() {
        return billingInterval;
    }

    public void setBillingInterval(BillingCycle billingInterval) {
        this.billingInterval = billingInterval;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FeeType getFeesType() {
        return feesType;
    }

    public void setFeesType(FeeType feesType) {
        this.feesType = feesType;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public List<TransactionRecord> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionRecord> transactions) {
        this.transactions = transactions;
    }

    public Client(ClientCreateRequest clientCreateRequest){
        this.id = clientCreateRequest.getId();
        this.client = clientCreateRequest.getClient();
        this.status = clientCreateRequest.getStatus();
        this.billingInterval = clientCreateRequest.getBillingInterval();
        this.email = clientCreateRequest.getEmail();
        this.feesType = clientCreateRequest.getFeesType();
        this.fees = clientCreateRequest.getFees();
    }
}
