package com.example.demoJpa.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Data
public class Auditable {

    public static final List<String> AUDITABLE_FIELDS = List.of("createdDate", "createdBy", "lastModifiedBy", "lastModifiedDate");

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
