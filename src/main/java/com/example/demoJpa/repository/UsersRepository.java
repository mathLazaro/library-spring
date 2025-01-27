package com.example.demoJpa.repository;

import com.example.demoJpa.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findUsersByUsername(String username);
}
