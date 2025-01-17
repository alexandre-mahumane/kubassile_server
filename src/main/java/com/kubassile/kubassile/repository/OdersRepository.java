package com.kubassile.kubassile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kubassile.kubassile.domain.client.Client;
import com.kubassile.kubassile.domain.order.Order;

public interface OdersRepository extends JpaRepository<Order, Long> {

    List<Order> findByClientId(Client client);

}
