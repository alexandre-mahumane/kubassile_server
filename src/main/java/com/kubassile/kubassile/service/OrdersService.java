package com.kubassile.kubassile.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.kubassile.kubassile.domain.client.Client;
import com.kubassile.kubassile.domain.client.dto.ClientDto;
import com.kubassile.kubassile.domain.order.Order;
import com.kubassile.kubassile.domain.order.dtos.OrderDto;
import com.kubassile.kubassile.domain.order.dtos.OrderResponseDto;
import com.kubassile.kubassile.domain.order.dtos.OrderDataResponseDto;
import com.kubassile.kubassile.domain.order.enums.Status;
import com.kubassile.kubassile.domain.payments.Payments;
import com.kubassile.kubassile.domain.payments.dtos.PaymentDto;
import com.kubassile.kubassile.exceptions.NotFoundException;
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
        private final DateTimeFormatter dateTimeFormatter;
        private final DocumentsService documentsService;

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
                                                        data.getPaymentStatusId(),
                                                        data.getCreatedAt().format(dateTimeFormatter),
                                                        data.getUpdatedAt().format(dateTimeFormatter)))
                                        .collect(Collectors.toList());
                }
                return this.paymentService.getAll();
        }

        public List<OrderDataResponseDto> getByClient(Long clientId) {

                Client client = this.clientRepository
                                .findById(clientId)
                                .orElseThrow(() -> new NotFoundException("Client not found"));
                List<Order> order = this.odersRepository.findByClientId(client);

                List<Payments> payment = this.paymentRepository.findByOrderIn(order);

                var dto = payment.stream()
                                .map(data -> new OrderDataResponseDto(
                                                data.getOrder(),
                                                data.getValue(),
                                                data.getPaymentMethodId(),
                                                data.getPaymentStatusId(),
                                                data.getCreatedAt().format(dateTimeFormatter),
                                                data.getUpdatedAt().format(dateTimeFormatter)))
                                .collect(Collectors.toList());
                return dto;
        }

        @Transactional
        public OrderResponseDto insert(OrderDto data) {
                var dto = new ClientDto(data.clientName(), data.phone());
                Client client = clientService.insert(dto);
                Order order = new Order();
                order.setClientId(client);
                order.setDescription(data.description());
                order.setType(data.orderType());
                order.setOrderStatus(Status.fromId(data.orderStatusId()));

                var saveOrder = this.odersRepository.save(order);

                PaymentDto paymentDto = new PaymentDto(
                                saveOrder,
                                data.paymentStatusId(),
                                data.value(),
                                data.paymentMethodId());
                var payment = this.paymentService.insert(paymentDto);

                this.documentsService.imprimirFatura(saveOrder, payment.paymentMethodId(), payment.paymentStatusId(), payment.value());
                return new OrderResponseDto(saveOrder.getId());

        }

        public OrderResponseDto update(Long orderId, OrderDto data) {
                Order order = this.odersRepository
                                .findById(orderId)
                                .orElseThrow(
                                                () -> new NotFoundException("Order not found"));

                ClientDto clientDto = new ClientDto(data.clientName(), data.phone());
                this.clientService.update(order.getClientId().getId(), clientDto);

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

                if (orderData.getOrderStatusId() == 2L && payment.paymentStatusId() == 2L){
                        this.documentsService.imprimirFatura(orderData, payment.paymentMethodId(), payment.paymentStatusId(), payment.value());
                }
                return new OrderResponseDto(orderData.getId());
        }

        public void delete(Long id) {
                Order order = this.odersRepository
                                .findById(id)
                                .orElseThrow(
                                                () -> new NotFoundException("Order not found"));

                this.paymentService.delete(order.getId());
                this.odersRepository.delete(order);
        }

}
