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
import com.kubassile.kubassile.exceptions.ForbiddenException;
import com.kubassile.kubassile.repository.UsersRepository;
import com.kubassile.utils.SetCookies;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    private  UsersRepository usersRepository;
    private  JWTService jwtService;
    private  AuthenticationManager authenticationManager;

    public TokenResponseDto signin(UserDto dto, HttpServletResponse response) {
        var checkUser = this.usersRepository.findByName(dto.username());

        if (checkUser != null) {
            throw new DataIntegrityViolationException("Username already exist");
        }
        Users user = new Users();
        user.setName(dto.username());
        user.setPass(new BCryptPasswordEncoder().encode(dto.password()));
        user.setRole(Roles.valueOf(dto.role()));

        this.usersRepository.save(user);
        String token = this.jwtService.generateToken(user);
        String refreshToken = this.jwtService.generateRefreshToken(user);

        SetCookies.SetCookie(refreshToken, response);

        return new TokenResponseDto(token);

    }

    public TokenResponseDto login(UserDto dto, HttpServletResponse response) {
        var checkUser = this.usersRepository.findByName(dto.username());

        if (checkUser == null) {
            throw new NotFoundException("User not found");
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var authentication = authenticationManager.authenticate(usernamePassword);
        String refreshToken = this.jwtService.generateRefreshToken((Users) authentication.getPrincipal());

        SetCookies.SetCookie(refreshToken, response);

        return new TokenResponseDto(this.jwtService.generateToken((Users) authentication.getPrincipal()));
    }

    public TokenResponseDto refreshToken(String token) {
        if (token.isBlank())
            throw new ForbiddenException("Token is invalid!");
        String subject = this.jwtService.decodeToken(token);

        var user = this.usersRepository.findByName(subject);

        return new TokenResponseDto(this.jwtService.generateToken((Users) user));
    }

    public void logout(HttpServletResponse response) {

        SetCookies.DeleteCookie(response);
    }
}
