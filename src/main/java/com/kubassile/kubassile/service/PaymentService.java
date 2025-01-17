package com.kubassile.kubassile.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kubassile.kubassile.domain.order.dtos.OrderDataResponseDto;
import com.kubassile.kubassile.domain.payments.Payments;
import com.kubassile.kubassile.domain.payments.dtos.PaymentDto;
import com.kubassile.kubassile.domain.payments.dtos.PaymentResponseDto;
import com.kubassile.kubassile.repository.OdersRepository;
import com.kubassile.kubassile.repository.PaymentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentService {

        private final PaymentRepository paymentRepository;
        private final OdersRepository odersRepository;

        public List<OrderDataResponseDto> getAll() {
                var data = paymentRepository.findAll();

                var dto = data.stream()
                                .map(payment -> new OrderDataResponseDto(
                                                payment.getOrder(),
                                                payment.getValue(),
                                                payment.getPaymentStatusId(),
                                                payment.getPaymentMethodId()))
                                .collect(Collectors.toList());
                return dto;
        }

        // public List<OrderDataResponseDto> getByClient(Long id) {
        // var data = paymentRepository.findAllByClient(id);

        // var dto = data.stream()
        // .map(payment -> new OrderDataResponseDto(
        // payment.getOrder(),
        // payment.getValue(),
        // payment.getPaymentStatusId(),
        // payment.getPaymentMethodId()))
        // .collect(Collectors.toList());
        // return dto;
        // }

        public PaymentResponseDto insert(PaymentDto data) {

                this.odersRepository.findById(data.order().getId())
                                .orElseThrow(() -> new RuntimeException("Order not found"));

                Payments payment = new Payments();

                payment.setOrder(data.order());
                payment.setValue(data.value());
                payment.setPaymentMethodId(data.paymentMethodId());
                payment.setPaymentStatusId(data.paymentStatusId());

                this.paymentRepository.save(payment);

                return new PaymentResponseDto(
                                payment.getId(),
                                payment.getOrder(),
                                payment.getPaymentStatusId(),
                                payment.getValue(),
                                payment.getPaymentMethodId());
        }

        public Payments update(PaymentDto data) {
                Payments payment = this.paymentRepository.findByOrderId(data.order().getId());

                if (!data.paymentMethodId().toString().isEmpty()) {
                        payment.setPaymentMethodId(data.paymentMethodId());
                }
                if (!data.paymentStatusId().toString().isEmpty()) {
                        payment.setPaymentStatusId(data.paymentStatusId());
                }
                if (!data.value().toString().isEmpty()) {
                        payment.setValue(data.value());
                }

                return this.paymentRepository.save(payment);
        }

        public void delete(Long orderId) {
                Payments payment = this.paymentRepository.findByOrderId(orderId);
                this.paymentRepository.delete(payment);
        }
}
