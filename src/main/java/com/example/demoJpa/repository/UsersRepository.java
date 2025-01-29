package com.example.demoJpa.repository;

import com.example.demoJpa.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    Optional<Users> findUsersByUsername(String username);
    Optional<Users> findUsersByEmail(String email);
}
