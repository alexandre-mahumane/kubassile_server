package com.kubassile.kubassile.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kubassile.kubassile.repository.UsersRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.usersRepository.findByName(username);

    }

}