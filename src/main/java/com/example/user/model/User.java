package com.example.user.model;

import com.example.utils.model.ABaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

/** Entity class for users. */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@SuperBuilder
public class User extends ABaseEntity {
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
}
