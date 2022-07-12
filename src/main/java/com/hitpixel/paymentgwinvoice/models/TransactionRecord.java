package com.hitpixel.paymentgwinvoice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hitpixel.paymentgwinvoice.models.enums.CardType;
import com.hitpixel.paymentgwinvoice.models.enums.TransactionStatus;
import com.hitpixel.paymentgwinvoice.requestdto.TxnCreateRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRecord {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String orderId;
    private String dateTime;
    private String orderName;
    private String amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    @Column(name = "cardType")
    private CardType cardType;
    @Enumerated(EnumType.STRING)
    @Column(name = "transactionStatus")
    private TransactionStatus transactionStatus;
    @ManyToOne
    @JsonIgnore
    private Client client;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    public TransactionRecord(TxnCreateRequest createRequest){
        this.id = createRequest.getId();
        this.orderId = createRequest.getOrderId();
        this.dateTime = createRequest.getDatetime();
        this.orderName = createRequest.getOrderName();
        this.amount = createRequest.getAmount();
        this.currency = createRequest.getCurrency();
        this.cardType = createRequest.getCardType();
        this.transactionStatus = createRequest.getStatus();
    }
}
