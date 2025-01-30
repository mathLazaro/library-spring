package com.example.demoJpa.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "client_id"))
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "client_id")
    private String clientId;
    private String clientSecret;
    private String clientName;
    private String redirectUri = "http://localhost:8080/authorized";
    private String scope;
}
