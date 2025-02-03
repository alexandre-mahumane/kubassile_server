package com.kubassile.kubassile.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kubassile.kubassile.domain.user.UserDto;
import com.kubassile.kubassile.domain.user.dto.TokenResponseDto;
import com.kubassile.kubassile.service.AuthService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class Auth {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDto> insert(@RequestBody UserDto data) {

        return new ResponseEntity<>(this.authService.signin(data), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody UserDto data) {

        return ResponseEntity.ok(this.authService.login(data));
    }

}
