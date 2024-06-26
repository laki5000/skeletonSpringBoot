package com.example.user.model;

import com.example.utils.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/** User entity. */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public User(Long id, String username, String password, Instant createdAt, String createdBy, Instant updatedAt, String updatedBy) {
        super(id, createdAt, createdBy, updatedAt, updatedBy);
        this.username = username;
        this.password = password;
    }

    public User copy() {
        return new User(super.getId(), username, password, super.getCreatedAt(), super.getCreatedBy(), super.getUpdatedAt(), super.getUpdatedBy());
    }
}
