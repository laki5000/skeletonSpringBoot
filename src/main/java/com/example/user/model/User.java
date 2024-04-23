package com.example.user.model;

import com.example.utils.model.BaseModelForCreation;
import jakarta.persistence.Entity;
import lombok.Getter;

/** User entity. */
@Entity
@Getter
public class User extends BaseModelForCreation {
    private String username;
    private String password;
}
