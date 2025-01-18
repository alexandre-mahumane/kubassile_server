package com.kubassile.kubassile.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kubassile.kubassile.domain.client.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByPhone(String phone);

    Client findByClientNameIgnoreCase(String clientName);

    Client findByClientNameAndPhoneIgnoreCase(String clientName, String phone);

    @Query(value = "SELECT * FROM Client c WHERE c.created_at BETWEEN :start AND :end", nativeQuery = true)
    List<Client> findAll(LocalDateTime start, LocalDateTime end);
}
