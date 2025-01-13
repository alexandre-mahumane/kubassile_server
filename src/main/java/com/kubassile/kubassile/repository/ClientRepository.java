package com.kubassile.kubassile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kubassile.kubassile.domain.client.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByClientName(String clientName);
    Optional<Client> findByPhone(String phone);
}
