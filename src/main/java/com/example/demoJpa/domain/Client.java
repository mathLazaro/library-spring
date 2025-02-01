package com.example.demoJpa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "client_id"))
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "client_id")
    private String clientId;
    private String clientSecret;
    private String redirectUri = "http://localhost:8080/authorized";
    private String scope;
}
