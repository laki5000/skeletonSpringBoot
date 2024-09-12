package com.example.domain.user.model;

import static com.example.constants.EntityConstants.USER_TABLE_NAME;
import static com.example.constants.FilteringConstants.FIELD_DETAILS_ID;
import static com.example.constants.FilteringConstants.FIELD_ID;
import static com.example.constants.ValidationConstants.USERNAME_MAX_LENGTH;
import static com.example.constants.ValidationConstants.USERNAME_MIN_LENGTH;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

import com.example.base.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

/** Entity class for users. */
@Entity
@Table(name = USER_TABLE_NAME)
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

    @NotNull
    @OneToOne(cascade = ALL)
    @JoinColumn(name = FIELD_DETAILS_ID, referencedColumnName = FIELD_ID, nullable = false)
    private UserDetails details;
}
