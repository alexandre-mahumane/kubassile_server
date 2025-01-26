package com.kubassile.kubassile.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kubassile.kubassile.domain.client.Client;
import com.kubassile.kubassile.domain.client.dto.ClientDto;
import com.kubassile.kubassile.domain.client.dto.ClientResponseDto;
import com.kubassile.kubassile.service.ClientService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/client")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/")
    public ResponseEntity<List<ClientResponseDto>> getClientsRegistration(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,

            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate) {
        return ResponseEntity.ok(this.clientService.getAll(startDate, endDate));
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
    public void delete(@PathVariable Long id) {
        this.clientService.deleteById(id);
        ;
    }

}
