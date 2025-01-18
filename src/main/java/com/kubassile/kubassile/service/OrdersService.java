package com.kubassile.kubassile.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kubassile.kubassile.domain.client.Client;
import com.kubassile.kubassile.domain.client.ClientDto;
import com.kubassile.kubassile.domain.order.Order;
import com.kubassile.kubassile.domain.order.dtos.OrderDto;
import com.kubassile.kubassile.domain.order.dtos.OrderResponseDto;
import com.kubassile.kubassile.domain.order.dtos.OrderDataResponseDto;
import com.kubassile.kubassile.domain.order.enums.Status;
import com.kubassile.kubassile.domain.payments.Payments;
import com.kubassile.kubassile.domain.payments.dtos.PaymentDto;
import com.kubassile.kubassile.repository.ClientRepository;
import com.kubassile.kubassile.repository.OrdersRepository;
import com.kubassile.kubassile.repository.PaymentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrdersService {

    private final OrdersRepository odersRepository;
    private final ClientService clientService;
    private final ClientRepository clientRepository;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    public List<OrderDataResponseDto> getAll(
            LocalDateTime startDate,
            LocalDateTime endDate) {
        if (startDate != null && endDate != null) {
            List<Order> orders = this.odersRepository.findAll(
                    startDate.withHour(0).withMinute(0).withSecond(0),
                    endDate.withHour(23).withMinute(59).withSecond(59));
            List<Payments> list = this.paymentRepository.findByOrderIn(orders);

            return list.stream()
                    .map(data -> new OrderDataResponseDto(
                            data.getOrder(),
                            data.getValue(),
                            data.getPaymentMethodId(),
                            data.getPaymentStatusId()))
                    .collect(Collectors.toList());
        }
        return this.paymentService.getAll();
    }

    public List<OrderDataResponseDto> getByClient(Long clientId) {

        Client client = this.clientRepository
                .findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        List<Order> order = this.odersRepository.findByClientId(client);

        List<Payments> payment = this.paymentRepository.findByOrderIn(order);

        var dto = payment.stream()
                .map(data -> new OrderDataResponseDto(
                        data.getOrder(),
                        data.getValue(),
                        data.getPaymentMethodId(),
                        data.getPaymentStatusId()))
                .collect(Collectors.toList());
        return dto;
    }

    public OrderResponseDto insert(OrderDto data) {
        var dto = new ClientDto(data.clientName(), data.phone());
        Client client = clientService.insert(dto);
        Order order = new Order();
        order.setClientId(client);
        order.setDescription(data.description());
        order.setType(data.orderType());
        order.setOrderStatus(Status.fromId(data.orderStatusId()));

        var saveOrder = this.odersRepository.save(order);

        PaymentDto payment = new PaymentDto(
                saveOrder,
                data.paymentStatusId(),
                data.value(),
                data.paymentMethodId());
        this.paymentService.insert(payment);
        return new OrderResponseDto(saveOrder.getId());

    }

    public OrderResponseDto update(Long orderId, OrderDto data) {
        Order order = this.odersRepository
                .findById(orderId)
                .orElseThrow(
                        () -> new RuntimeException(""));

        if (!data.description().isEmpty()) {
            order.setDescription(data.description());
        }
        if (!data.orderStatusId().toString().isEmpty()) {
            order.setOrderStatus(Status.fromId(data.orderStatusId()));
        }
        if (!data.orderType().isEmpty()) {
            order.setType(data.orderType());
        }

        var orderData = this.odersRepository.save(order);
        PaymentDto payment = new PaymentDto(
                orderData,
                data.paymentStatusId(),
                data.value(),
                data.paymentMethodId());
        this.paymentService.update(payment);
        return new OrderResponseDto(orderData.getId());
    }

    public void delete(Long id) {
        Order order = this.odersRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException(""));

        this.paymentService.delete(order.getId());
        this.odersRepository.delete(order);
    }

}
