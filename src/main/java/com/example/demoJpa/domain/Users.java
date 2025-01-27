package com.example.demoJpa.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(uniqueConstraints = @UniqueConstraint(name = "unique_username", columnNames = {"username"}))
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(length = 500, nullable = false)
    private String password;
    private String name;
    @Column
    @Enumerated(EnumType.STRING)
    private RoleUser roleUser = RoleUser.OPERATOR;

}
