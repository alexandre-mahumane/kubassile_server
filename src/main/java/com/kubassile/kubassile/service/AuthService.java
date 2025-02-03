package com.kubassile.kubassile.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kubassile.kubassile.domain.user.UserDto;
import com.kubassile.kubassile.domain.user.Users;
import com.kubassile.kubassile.domain.user.dto.TokenResponseDto;
import com.kubassile.kubassile.domain.user.enums.Roles;
import com.kubassile.kubassile.exceptions.DataIntegrityViolationException;
import com.kubassile.kubassile.exceptions.NotFoundException;
import com.kubassile.kubassile.repository.UsersRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    private final UsersRepository usersRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponseDto signin(UserDto dto) {
        var checkUser = this.usersRepository.findByName(dto.username());

        if (checkUser != null)
            throw new DataIntegrityViolationException("Username already exists");

        Users user = new Users();
        user.setName(dto.username());
        user.setPass(new BCryptPasswordEncoder().encode(dto.password()));
        user.setRole(Roles.valueOf(dto.role()));

        this.usersRepository.save(user);
        return new TokenResponseDto(this.jwtService.generateToken(user));

    }

    public TokenResponseDto login(UserDto dto) {
        var checkUser = this.usersRepository.findByName(dto.username());

        if (checkUser == null)
            throw new NotFoundException("User not found");

        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var authentication = authenticationManager.authenticate(usernamePassword);

        return new TokenResponseDto(this.jwtService.generateToken((Users) authentication.getPrincipal()));
    }

}
