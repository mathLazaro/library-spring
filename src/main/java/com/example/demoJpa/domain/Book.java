package com.example.demoJpa.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@ToString
@Table(uniqueConstraints = @UniqueConstraint(name = "isbn_unique", columnNames = {"isbn"}))
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    String isbn;
    @Column(length = 512)
    String title;
    LocalDate publishDate;
    String genre;
    @Column(precision = 5, scale = 2)
    BigDecimal price = BigDecimal.valueOf(0);

    @JsonBackReference(value = "book-author")
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    // Auditing
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
    @CreatedBy
    @ManyToOne
    @JoinColumn
    private Users createdBy;
    @LastModifiedBy
    @ManyToOne
    @JoinColumn
    private Users lastModifiedBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate = LocalDateTime.now();
}
