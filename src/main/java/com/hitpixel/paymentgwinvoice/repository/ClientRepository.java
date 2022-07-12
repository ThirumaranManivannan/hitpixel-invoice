package com.hitpixel.paymentgwinvoice.repository;

import com.hitpixel.paymentgwinvoice.models.Client;
import com.hitpixel.paymentgwinvoice.models.enums.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    public Client findByClient(String clientName);
    public Client findByClientAndStatus(String clientName, ClientStatus clientStatus);
}
