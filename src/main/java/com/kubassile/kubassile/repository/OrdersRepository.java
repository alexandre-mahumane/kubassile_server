package com.kubassile.kubassile.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kubassile.kubassile.domain.client.Client;
import com.kubassile.kubassile.domain.order.Order;

public interface OrdersRepository extends JpaRepository<Order, Long> {

        List<Order> findByClientId(Client client);

        @Query(value = "SELECT * FROM Orders o WHERE o.created_at BETWEEN :start AND :end", nativeQuery = true)
        List<Order> findAll(LocalDateTime start, LocalDateTime end);

}
