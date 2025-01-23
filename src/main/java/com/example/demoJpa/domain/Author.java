package com.example.demoJpa.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Builder
@ToString(exclude = "bookList")
@Table(uniqueConstraints = {@UniqueConstraint(name = "uniqueAuthor", columnNames = {"name", "birthDate", "nationality"})})
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private String nationality;

    // auditing fields
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
    @LastModifiedBy
    private Integer lastModifiedBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate = LocalDateTime.now();


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = CascadeType.PERSIST)
    @JsonManagedReference(value = "book-author")
     private List<Book> bookList;

    public Author(Integer id) {

        this.id = id;
    }
}
