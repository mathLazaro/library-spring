package com.example.demoJpa.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class Book {

    // TODO - criar camada de Service e Controller do Livro
    // TODO - adaptar campos ao requisito
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(length = 1024)
    private String description;

    @JsonBackReference(value = "book-author")
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
}
