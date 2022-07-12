package com.hitpixel.paymentgwinvoice.responsedto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hitpixel.paymentgwinvoice.models.Invoice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceCustom {
    @JsonProperty("invoice_id")
    private String invoiceId;
    @JsonProperty("transactions")
    private Integer totalTransactions;
    @JsonProperty("approved_transactions")
    private Integer totalApprovedTransactions;
    @JsonProperty("refunded_transactions")
    private Integer totalRefundedTransactions;
    @JsonProperty("declined_transactions")
    private Integer totalDeclinedTransactions;
    @JsonProperty("total_amount")
    private Double totalAmount;
    @JsonProperty("approved_amount")
    private Double approvedAmount;
    @JsonProperty("refunded_amount")
    private Double refundedAmount;

    public InvoiceCustom(Invoice invoice){
        this.invoiceId = invoice.getId();
        this.totalTransactions = invoice.getTotalTransactions();
        this.totalApprovedTransactions = invoice.getApprovedTransactions();
        this.totalRefundedTransactions = invoice.getRefundedTransactions();
        this.totalDeclinedTransactions = invoice.getDeclinedTransactions();
        this.totalAmount = invoice.getTotalAmount();
        this.refundedAmount = invoice.getRefundedAmount();
        this.approvedAmount = invoice.getApprovedAmount();
    }
}
