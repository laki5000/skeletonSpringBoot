package com.example.user.model;

import com.example.utils.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

/** User entity. */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
@SuperBuilder
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public User copy() {
        return User.builder()
                .id(this.getId())
                .username(this.getUsername())
                .password(this.getPassword())
                .createdAt(this.getCreatedAt())
                .createdBy(this.getCreatedBy())
                .updatedAt(this.getUpdatedAt())
                .updatedBy(this.getUpdatedBy())
                .build();
    }
}
