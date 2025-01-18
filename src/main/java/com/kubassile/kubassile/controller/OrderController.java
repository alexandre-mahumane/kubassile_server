package com.kubassile.kubassile.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kubassile.kubassile.domain.order.dtos.OrderDto;
import com.kubassile.kubassile.domain.order.dtos.OrderResponseDto;
import com.kubassile.kubassile.domain.order.dtos.OrderDataResponseDto;
import com.kubassile.kubassile.service.OrdersService;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@AllArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrdersService odersService;

    @GetMapping("/")
    public ResponseEntity<List<OrderDataResponseDto>> getAll(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,

            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate) {
        return ResponseEntity.ok(this.odersService.getAll(startDate, endDate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OrderDataResponseDto>> getByClient(@PathVariable Long id) {
        return ResponseEntity.ok(this.odersService.getByClient(id));
    }

    @PostMapping("/")
    public ResponseEntity<OrderResponseDto> insert(@RequestBody OrderDto data) {

        return new ResponseEntity<>(this.odersService.insert(data), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> update(@PathVariable Long id, @RequestBody OrderDto data) {
        return ResponseEntity.ok(this.odersService.update(id, data));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.odersService.delete(id);
    }

}
