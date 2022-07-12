package com.hitpixel.paymentgwinvoice.repository;

import com.hitpixel.paymentgwinvoice.models.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionRecord, String> {
    public TransactionRecord findByOrderId(String orderId);
}
