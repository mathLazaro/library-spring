package com.example.demoJpa.domain;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column
    @Enumerated(EnumType.STRING)
    private RoleUser roleUser = RoleUser.OPERATOR;
}
