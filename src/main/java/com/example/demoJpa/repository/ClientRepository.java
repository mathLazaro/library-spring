package com.example.demoJpa.repository;

import com.example.demoJpa.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    boolean existsByClientId(String clientId);
    Optional<Client> findByClientId(String clientId);
}
