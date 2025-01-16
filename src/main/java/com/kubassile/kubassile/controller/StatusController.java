package com.kubassile.kubassile.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kubassile.kubassile.domain.order.dtos.StatusDto;
import com.kubassile.kubassile.domain.order.enums.Status;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/status")
@AllArgsConstructor
public class StatusController {

    @GetMapping("/order")
    public ResponseEntity<List<StatusDto>> getOrderStatus() {
        return ResponseEntity.ok(Status.getAll());
    }
    
}
