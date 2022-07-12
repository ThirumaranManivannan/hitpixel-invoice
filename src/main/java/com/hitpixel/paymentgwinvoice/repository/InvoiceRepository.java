package com.hitpixel.paymentgwinvoice.repository;

import com.hitpixel.paymentgwinvoice.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    public List<Invoice> findByIsGenerated(Boolean isGenerated);
}
