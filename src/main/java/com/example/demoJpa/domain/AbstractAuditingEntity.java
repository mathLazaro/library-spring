package com.example.demoJpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdDate = LocalDate.now();

    @LastModifiedBy
    private Integer lastModifiedBy;

    @LastModifiedDate
    private LocalDate lastModifiedDate = LocalDate.now();

}
