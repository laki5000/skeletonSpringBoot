package com.example.user.model;

import com.example.utils.model.BaseModelForCreation;
import jakarta.persistence.Entity;

@Entity
public class User extends BaseModelForCreation {
    private String username;
    private String password;
}
