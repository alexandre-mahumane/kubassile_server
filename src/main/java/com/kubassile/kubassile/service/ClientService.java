package com.kubassile.kubassile.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kubassile.kubassile.domain.client.Client;
import com.kubassile.kubassile.domain.client.ClientDto;
import com.kubassile.kubassile.repository.ClientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public List<Client> getAll(){
        return this.clientRepository.findAll();
    }


    public Client insert(ClientDto data){
        System.out.println(data);
        var checkClient = this.clientRepository
            .findByClientName(data.clientName());
            
        if (checkClient.isPresent()) {
            throw new RuntimeException("User ");
        }
        var checkPhone = this.clientRepository
            .findByPhone(data.phone());
            
        if (checkPhone.isPresent()) {
            throw new RuntimeException("User ");
        }

        Client client = new Client(data);
        
        return this.clientRepository.save(client);
        }

    public Client update(Long id, ClientDto data){
        Client client = 
            this.clientRepository
                .findById(id)
                .orElseThrow(
                    ()-> new RuntimeException("Client not found"));

        if(!data.clientName().isEmpty()) client.setClientName(data.clientName());
        if(!data.phone().isEmpty()) client.setPhone(data.phone());

        return this.clientRepository.save(client);
    }

    public void deleteById(Long id){
        Client client = 
            this.clientRepository
                .findById(id)
                .orElseThrow(
                    ()-> new RuntimeException("Client not found"));
        this.clientRepository.delete(client);
    }
}
