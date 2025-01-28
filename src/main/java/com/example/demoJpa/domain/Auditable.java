package com.example.demoJpa.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class Auditable {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
    @CreatedBy
    @ManyToOne
    @JoinColumn(updatable = false)
    private Users createdBy;
    @ManyToOne
    @LastModifiedBy
    @JoinColumn
    private Users lastModifiedBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

}
