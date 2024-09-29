package com.example.domain.user.model;

import static com.example.constants.ValidationConstants.*;
import static jakarta.persistence.GenerationType.IDENTITY;

import com.example.base.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/** Entity class for user details */
@Entity
@Table(name = "user_details")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class UserDetails extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @Size(min = FIRST_NAME_MIN_LENGTH, max = FIRST_NAME_MAX_LENGTH)
    @Column(nullable = false)
    private String firstName;

    @NotNull
    @Size(min = LAST_NAME_MIN_LENGTH, max = LAST_NAME_MAX_LENGTH)
    @Column(nullable = false)
    private String lastName;
}
