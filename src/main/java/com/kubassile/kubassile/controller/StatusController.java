package com.kubassile.kubassile.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kubassile.kubassile.domain.order.dtos.StatusDto;
import com.kubassile.kubassile.domain.order.enums.Status;
import com.kubassile.kubassile.domain.payments.dtos.PaymentStatusDto;
import com.kubassile.kubassile.domain.payments.enums.PaymentMethod;
import com.kubassile.kubassile.domain.payments.enums.PaymentStatus;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class StatusController {

    @GetMapping("/order/status")
    public ResponseEntity<List<StatusDto>> getOrderStatus() {
        return ResponseEntity.ok(Status.getAll());
    }

    @GetMapping("/payments/status")
    public ResponseEntity<List<PaymentStatusDto>> getPaymentStatus() {
        return ResponseEntity.ok(PaymentStatus.getAll());
    }

    @GetMapping("/payments/method")
    public ResponseEntity<List<PaymentStatusDto>> getPaymentMethod() {
        return ResponseEntity.ok(PaymentMethod.getAll());
    }

}
