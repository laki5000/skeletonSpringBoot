package com.example.user.model;

import com.example.utils.model.ABaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/** Entity class for users. */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends ABaseEntity {
  @NotNull
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Size(min = 3, max = 20)
  @Column(nullable = false, unique = true)
  private String username;

  @NotNull
  @Column(nullable = false)
  private String password;
}
