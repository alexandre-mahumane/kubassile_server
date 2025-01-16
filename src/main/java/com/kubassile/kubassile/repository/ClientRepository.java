package com.kubassile.kubassile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kubassile.kubassile.domain.client.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByPhone(String phone);
    Client findByClientNameIgnoreCase(String clientName);
    Client findByClientNameAndPhoneIgnoreCase(String clientName, String phone);
}
