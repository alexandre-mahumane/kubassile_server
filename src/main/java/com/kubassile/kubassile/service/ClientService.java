package com.kubassile.kubassile.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kubassile.kubassile.domain.client.Client;
import com.kubassile.kubassile.domain.client.dto.ClientDto;
import com.kubassile.kubassile.domain.client.dto.ClientResponseDto;
import com.kubassile.kubassile.exceptions.NotFoundException;
import com.kubassile.kubassile.repository.ClientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final DateTimeFormatter dateTimeFormatter;

    public List<ClientResponseDto> getAll(
            LocalDateTime startDate,
            LocalDateTime endDate) {
        if ((startDate != null && endDate != null)) {
            List<Client> clients = this.clientRepository.findAll(
                    startDate.withHour(0).withMinute(0).withSecond(0),
                    endDate.withHour(23).withMinute(59).withSecond(59));

            return clients.stream()
                    .map(data -> new ClientResponseDto(data.getId(), data.getClientName(), data.getPhone(),
                            data.getCreatedAt().format(dateTimeFormatter)))
                    .collect(Collectors.toList());
        }
        List<Client> clients = this.clientRepository.findAll();

        return clients.stream().map(data -> new ClientResponseDto(data.getId(), data.getClientName(), data.getPhone(),
                data.getCreatedAt().format(dateTimeFormatter))).collect(Collectors.toList());
    }

    public Client insert(ClientDto data) {
        Client checkClient = this.clientRepository
                .findByClientNameAndPhoneIgnoreCase(data.clientName(), data.phone());
        if (checkClient != null) {
            return checkClient;
        }

        Client client = new Client(data);

        return this.clientRepository.save(client);
    }

    public Client update(Long id, ClientDto data) {
        Client client = this.clientRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Client not found"));

        if (!data.clientName().isEmpty())
            client.setClientName(data.clientName());
        if (!data.phone().isEmpty())
            client.setPhone(data.phone());

        return this.clientRepository.save(client);
    }

    public void deleteById(Long id) {
        Client client = this.clientRepository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Client not found"));
        this.clientRepository.delete(client);
    }
}
