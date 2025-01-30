package com.example.demoJpa.repository;

import com.example.demoJpa.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    boolean existsByClientId(String clientId);
    Optional<Client> findByClientId(String clientId);
}
