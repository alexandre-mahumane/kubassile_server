package com.kubassile.kubassile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kubassile.kubassile.domain.order.Order;
import com.kubassile.kubassile.domain.payments.Payments;

public interface PaymentRepository extends JpaRepository<Payments, Long> {

    Payments findByOrderId(Long orderId);

    List<Payments> findByOrderIn(List<Order> order);

}
