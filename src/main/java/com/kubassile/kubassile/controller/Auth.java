package com.kubassile.kubassile.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kubassile.kubassile.domain.user.UserDto;
import com.kubassile.kubassile.domain.user.dto.TokenResponseDto;
import com.kubassile.kubassile.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class Auth {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDto> insert(@RequestBody UserDto data, HttpServletResponse response) {

        return new ResponseEntity<>(this.authService.signin(data, response), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody UserDto data, HttpServletResponse response) {

        return ResponseEntity.ok(this.authService.login(data, response));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponseDto> refreshToken(
            @CookieValue(value = "token", defaultValue = "") String token) {
        System.out.println("tokennn: " + token);
        return ResponseEntity.ok(this.authService.refreshToken(token));
    }

    @PostMapping("/logout")
    public void logout(
            @CookieValue(value = "token", defaultValue = "") String token, HttpServletResponse response) {
        this.authService.logout(response);
    }

}
