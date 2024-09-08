package com.example.user.model;

import static com.example.utils.constants.ValidationConstants.USERNAME_MAX_LENGTH;
import static com.example.utils.constants.ValidationConstants.USERNAME_MIN_LENGTH;
import static jakarta.persistence.GenerationType.IDENTITY;

import com.example.utils.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

/** Entity class for users. */
@Entity
@Table(name = "users")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @Size(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH)
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull
    @Column(nullable = false)
    private String password;
}
