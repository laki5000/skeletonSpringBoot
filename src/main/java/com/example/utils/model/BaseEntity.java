package com.example.utils.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/** Abstract class with common fields for entities. */
@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
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
