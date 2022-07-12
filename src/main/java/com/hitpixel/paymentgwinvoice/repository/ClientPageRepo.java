package com.hitpixel.paymentgwinvoice.repository;

import com.hitpixel.paymentgwinvoice.models.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientPageRepo extends PagingAndSortingRepository<Client, String> {
}
