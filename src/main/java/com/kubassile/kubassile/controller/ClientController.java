package com.kubassile.kubassile.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kubassile.kubassile.domain.client.Client;
import com.kubassile.kubassile.domain.client.ClientDto;
import com.kubassile.kubassile.service.ClientService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api/client")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/")
    public List<Client> getClients() {
        return this.clientService.getAll();
    }
    

    @PostMapping("/")
    public Client insert(@RequestBody ClientDto data) {
        return this.clientService.insert(data);
    }

    @PutMapping("/{id}")
    public Client update(
                        @PathVariable Long id, 
                        @RequestBody ClientDto data) {

        return this.clientService.update(id, data);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.clientService.deleteById(id);;
    }
    

}
