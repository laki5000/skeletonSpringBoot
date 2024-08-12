package com.example.utils.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/** Superclass for common fields in entities. */
@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseEntity {
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @NotNull
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    private String updatedBy;
}
