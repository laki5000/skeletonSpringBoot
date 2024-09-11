package com.example.domain.user.model;

import static com.example.utils.constants.EntityConstants.USER_DETAILS_TABLE_NAME;
import static jakarta.persistence.GenerationType.IDENTITY;

import com.example.utils.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/** Entity class for user details */
@Entity
@Table(name = USER_DETAILS_TABLE_NAME)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserDetails extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String firstName;

    @NotNull
    @Column(nullable = false)
    private String lastName;
}
