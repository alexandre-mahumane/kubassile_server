package com.kubassile.kubassile.service;

import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.kubassile.kubassile.domain.client.Client;
import com.kubassile.kubassile.domain.client.ClientDto;
import com.kubassile.kubassile.domain.order.Order;
import com.kubassile.kubassile.domain.order.dtos.OrderDto;
import com.kubassile.kubassile.domain.order.dtos.OrderResponseDto;
import com.kubassile.kubassile.domain.order.enums.Status;
import com.kubassile.kubassile.repository.ClientRepository;
import com.kubassile.kubassile.repository.OdersRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrdersService {
    
    private final OdersRepository odersRepository;
    private final ClientService clientService;
    private final ClientRepository clientRepository;


    public Stream<OrderResponseDto> getAll(){
        var order = this.odersRepository.findAll();
        var dto = order.stream().map(data -> new OrderResponseDto(data.getClientId(), data.getOrderStatusId(), data.getType(), data.getDescription()));

        return dto;
    }

    public Stream<OrderResponseDto> getByClient(Long clientId){

        Client client = 
            this.clientRepository
            .findById(clientId)
            .orElseThrow(()-> new RuntimeException("Client not found"));
            var order = this.odersRepository.findByClientId(client);
           var dto = order.stream().map(data -> new OrderResponseDto(data.getClientId(), data.getOrderStatusId(), data.getType(), data.getDescription()));
        return dto;
    }

    public Order insert(OrderDto data){
        var dto = new ClientDto(data.clientName(), data.phone());
        Client client = clientService.insert(dto);
                Order order = new Order();
        order.setClientId(client);
        order.setDescription(data.description());
        order.setType(data.orderType());
        order.setOrderStatus(Status.fromId(data.orderStatusId()));

        return this.odersRepository.save(order);

    }
    
    public Order update(Long orderId, OrderDto data){
        Order order  = this.odersRepository
            .findById(orderId)
                .orElseThrow(
                    ()-> new RuntimeException(""));
        
        if(!data.description().isEmpty()) order.setDescription(data.description());
        if(!data.orderStatusId().toString().isEmpty()) order.setOrderStatus(Status.fromId(data.orderStatusId()));
        if (!data.orderType().isEmpty()) order.setType(data.orderType());
    
        return this.odersRepository.save(order);
    }

    public  void delete(Long id){
        Order order  = this.odersRepository
            .findById(id)
                .orElseThrow(
                    ()-> new RuntimeException(""));

        this.odersRepository.delete(order);
    }

}
