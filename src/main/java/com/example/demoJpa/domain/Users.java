package com.example.demoJpa.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_username", columnNames = {"username"}),
        @UniqueConstraint(name = "unique_email", columnNames = {"email"})})
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(length = 500, nullable = false)
    private String password;
    private String name;
    private String email;
    @Column
    @Enumerated(EnumType.STRING)
    private RoleUser roleUser = RoleUser.OPERATOR;

}
