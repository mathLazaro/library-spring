package com.example.demoJpa.controller.dto.client;

import com.example.demoJpa.domain.Client;
import com.example.demoJpa.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postClient(@RequestBody Client client) {

        clientService.persistClient(client);
    }
}
