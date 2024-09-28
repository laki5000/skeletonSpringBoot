package com.example;

import com.example.domain.user.dto.request.UserCreateRequestDTO;
import com.example.domain.user.dto.request.UserDetailsRequestDTO;
import com.example.domain.user.dto.request.UserUpdateRequestDTO;
import com.example.domain.user.dto.response.UserDetailsResponseDTO;
import com.example.domain.user.dto.response.UserResponseDTO;
import com.example.domain.user.model.User;
import com.example.domain.user.model.UserDetails;
import com.example.enums.FilterOperator;
import com.example.utils.dto.request.FilteringDTO;
import java.time.Instant;

public class TestUtils {

    /**
     * Builds a user create request DTO with the given properties.
     *
     * @param username The username
     * @param password The password
     * @param firstName The first name
     * @param lastName The last name
     * @return The user create request DTO
     */
    public static UserCreateRequestDTO buildUserCreateRequestDTO(
            String username, String password, String firstName, String lastName) {
        UserDetailsRequestDTO userDetailsRequestDTO =
                buildUserDetailsRequestDTO(firstName, lastName);

        return UserCreateRequestDTO.builder()
                .username(username)
                .password(password)
                .details(userDetailsRequestDTO)
                .build();
    }

    /**
     * Builds a user update request DTO with the given properties.
     *
     * @param password The password
     * @param firstName The first name
     * @param lastName The last name
     * @return The user update request DTO
     */
    public static UserUpdateRequestDTO buildUserUpdateRequestDTO(
            String password, String firstName, String lastName) {
        UserDetailsRequestDTO userDetailsRequestDTO =
                buildUserDetailsRequestDTO(firstName, lastName);

        return UserUpdateRequestDTO.builder()
                .password(password)
                .details(userDetailsRequestDTO)
                .build();
    }

    /**
     * Builds a user response DTO with the given properties.
     *
     * @param id The ID
     * @param username The username
     * @param createdAt The created at
     * @param updatedAt The updated at
     * @param createdBy The created by
     * @param updatedBy The updated by
     * @param detailsId The details ID
     * @param detailsFirstName The first name
     * @param detailsLastName The last name
     * @param detailsCreatedAt The details created at
     * @param detailsUpdatedAt The details updated at
     * @param detailsCreatedBy The details created by
     * @param detailsUpdatedBy The details updated by
     * @return The user response DTO
     */
    public static User buildUser(
            Long id,
            String username,
            String password,
            Instant createdAt,
            Instant updatedAt,
            String createdBy,
            String updatedBy,
            Long detailsId,
            String detailsFirstName,
            String detailsLastName,
            Instant detailsCreatedAt,
            Instant detailsUpdatedAt,
            String detailsCreatedBy,
            String detailsUpdatedBy) {
        UserDetails userDetails =
                buildUserDetails(
                        detailsId,
                        detailsFirstName,
                        detailsLastName,
                        detailsCreatedAt,
                        detailsUpdatedAt,
                        detailsCreatedBy,
                        detailsUpdatedBy);

        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .createdBy(createdBy)
                .updatedBy(updatedBy)
                .details(userDetails)
                .build();
    }

    /**
     * Builds a user response DTO with the given properties.
     *
     * @param id The ID
     * @param username The username
     * @param createdAt The created at
     * @param updatedAt The updated at
     * @param createdBy The created by
     * @param updatedBy The updated by
     * @param detailsId The details ID
     * @param firstName The first name
     * @param lastName The last name
     * @param detailsCreatedAt The details created at
     * @param detailsUpdatedAt The details updated at
     * @param detailsCreatedBy The details created by
     * @param detailsUpdatedBy The details updated by
     * @return The user response DTO
     */
    public static UserResponseDTO buildUserResponseDTO(
            Long id,
            String username,
            Instant createdAt,
            Instant updatedAt,
            String createdBy,
            String updatedBy,
            Long detailsId,
            String firstName,
            String lastName,
            Instant detailsCreatedAt,
            Instant detailsUpdatedAt,
            String detailsCreatedBy,
            String detailsUpdatedBy) {
        UserDetailsResponseDTO userDetailsResponseDTO =
                buildUserDetailsResponseDTO(
                        detailsId,
                        firstName,
                        lastName,
                        detailsCreatedAt,
                        detailsUpdatedAt,
                        detailsCreatedBy,
                        detailsUpdatedBy);

        return UserResponseDTO.builder()
                .id(id)
                .username(username)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .createdBy(createdBy)
                .updatedBy(updatedBy)
                .details(userDetailsResponseDTO)
                .build();
    }

    /**
     * Builds a user details with the given properties.
     *
     * @param id The ID
     * @param firstName The first name
     * @param lastName The last name
     * @param createdAt The created at
     * @param updatedAt The updated at
     * @param createdBy The created by
     * @param updatedBy The updated by
     * @return The user details
     */
    public static UserDetails buildUserDetails(
            Long id,
            String firstName,
            String lastName,
            Instant createdAt,
            Instant updatedAt,
            String createdBy,
            String updatedBy) {
        return UserDetails.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .createdBy(createdBy)
                .updatedBy(updatedBy)
                .build();
    }

    /**
     * Builds a user details request DTO with the given properties.
     *
     * @param firstName The first name
     * @param lastName The last name
     * @return The user details request DTO
     */
    public static UserDetailsRequestDTO buildUserDetailsRequestDTO(
            String firstName, String lastName) {
        return UserDetailsRequestDTO.builder().firstName(firstName).lastName(lastName).build();
    }

    /**
     * Builds a user details response DTO with the given properties.
     *
     * @param id The ID
     * @param firstName The first name
     * @param lastName The last name
     * @param createdAt The created at
     * @param updatedAt The updated at
     * @param createdBy The created by
     * @param updatedBy The updated by
     * @return The user details response DTO
     */
    public static UserDetailsResponseDTO buildUserDetailsResponseDTO(
            Long id,
            String firstName,
            String lastName,
            Instant createdAt,
            Instant updatedAt,
            String createdBy,
            String updatedBy) {
        return UserDetailsResponseDTO.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .createdBy(createdBy)
                .updatedBy(updatedBy)
                .build();
    }

    /**
     * Builds a filtering DTO with the given properties.
     *
     * @param field The field
     * @param operator The operator
     * @param value The value
     * @return The filtering DTO
     */
    public static FilteringDTO buildFilteringDTO(
            String field, FilterOperator operator, String value) {
        return FilteringDTO.builder().field(field).operator(operator).value(value).build();
    }
}
