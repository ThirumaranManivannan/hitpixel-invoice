package com.hitpixel.paymentgwinvoice.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private Boolean isGenerated;
    private Integer totalTransactions;
    private Integer approvedTransactions;
    private Integer declinedTransactions;
    private Integer refundedTransactions;
    private Double totalAmount;
    private Double approvedAmount;
    private Double refundedAmount;
    @ManyToOne
    private Client client;
    @OneToMany
    private List<TransactionRecord> transactions = new ArrayList<>();

    public List<TransactionRecord> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionRecord> transactions) {
        this.transactions = transactions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getGenerated() {
        return isGenerated;
    }

    public void setGenerated(Boolean generated) {
        isGenerated = generated;
    }

    public Integer getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(Integer totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public Integer getApprovedTransactions() {
        return approvedTransactions;
    }

    public void setApprovedTransactions(Integer approvedTransactions) {
        this.approvedTransactions = approvedTransactions;
    }

    public Integer getDeclinedTransactions() {
        return declinedTransactions;
    }

    public void setDeclinedTransactions(Integer declinedTransactions) {
        this.declinedTransactions = declinedTransactions;
    }

    public Integer getRefundedTransactions() {
        return refundedTransactions;
    }

    public void setRefundedTransactions(Integer refundedTransactions) {
        this.refundedTransactions = refundedTransactions;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(Double approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public Double getRefundedAmount() {
        return refundedAmount;
    }

    public void setRefundedAmount(Double refundedAmount) {
        this.refundedAmount = refundedAmount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Invoice(Client client){
        this.isGenerated = false;
        this.totalTransactions = 0;
        this.approvedTransactions = 0;
        this.declinedTransactions = 0;
        this.refundedTransactions = 0;
        this.totalAmount = 0.0;
        this.approvedAmount = 0.0;
        this.refundedAmount = 0.0;
        this.client = client;
    }
}
