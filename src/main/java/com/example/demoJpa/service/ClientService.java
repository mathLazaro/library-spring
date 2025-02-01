package com.example.demoJpa.service;

import com.example.demoJpa.domain.Client;
import com.example.demoJpa.exception.InvalidCheckException;
import com.example.demoJpa.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    public void persistClient(Client client) {

        if (clientRepository.existsByClientId(client.getClientId()))
            throw new InvalidCheckException("Client id já cadastrado");
        client.setClientSecret(encoder.encode(client.getClientSecret()));
        clientRepository.save(client);
    }

    public void persistClient(RegisteredClient client) {

        if (clientRepository.existsByClientId(client.getClientId()))
            throw new InvalidCheckException("Client id já cadastrado");
        Client clientToSave = Client.builder()
                .clientId(client.getClientId())
                .clientSecret(encoder.encode(client.getClientSecret()))
                .redirectUri(client.getRedirectUris().stream().findFirst().orElse(null))
                .build();

        clientRepository.save(clientToSave);
    }


    public Client getClientByClientId(String clientId) {

        Optional<Client> client = clientRepository.findByClientId(clientId);
        if (client.isEmpty())
            throw new EntityNotFoundException("Client não encontrado");
        return client.get();
    }

    public Client getClientById(UUID id) {

        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty())
            throw new EntityNotFoundException("Client não encontrado");
        return client.get();
    }
}
