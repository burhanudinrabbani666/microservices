package com.eazybytes.accounts.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * BaseEntity
 */
@Getter
@Setter
@ToString
@MappedSuperclass
public class BaseEntity {

    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(updatable = false, name = "created_by")
    private String createdBy;

    @Column(insertable = false, name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(insertable = false, name = "updated_by")
    private String updatedBy;

}
