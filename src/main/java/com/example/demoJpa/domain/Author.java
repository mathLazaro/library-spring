package com.example.demoJpa.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Builder
@ToString(exclude = "bookList")
@Table(uniqueConstraints = {@UniqueConstraint(name = "uniqueAuthor", columnNames = {"name", "birthDate", "nationality"})})
@NoArgsConstructor
@AllArgsConstructor
public class Author extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private String nationality;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = CascadeType.PERSIST)
    @JsonManagedReference(value = "book-author")
    private List<Book> bookList;

    public Author(Integer id) {

        this.id = id;
    }
}
