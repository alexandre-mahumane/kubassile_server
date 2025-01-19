package com.kubassile.kubassile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kubassile.kubassile.domain.user.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findByName(String username);

}
