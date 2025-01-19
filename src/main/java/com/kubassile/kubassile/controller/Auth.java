package com.kubassile.kubassile.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kubassile.kubassile.domain.user.UserDto;
import com.kubassile.kubassile.domain.user.Users;
import com.kubassile.kubassile.service.JWTService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class Auth {

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String insert(@RequestBody UserDto data) {

        return "entity";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto data) {
        var userPassword = new UsernamePasswordAuthenticationToken(
                data.username(), data.password());

        System.out.println("user password " + userPassword);
        var authentication = this.authenticationManager.authenticate(userPassword);

        var token = (Users) authentication.getPrincipal();

        System.out.println("Enter in jwt service");
        String val = this.jwtService.generateToken(token);

        return ResponseEntity.ok("token");
    }

}
