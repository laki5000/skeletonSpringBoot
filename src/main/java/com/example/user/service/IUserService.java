package com.example.user.service;

import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.request.UserUpdateRequestDTO;
import com.example.user.dto.response.UserGetResponseDTO;
import com.example.user.model.User;
import java.util.Map;
import org.springframework.data.domain.Page;

/** Service interface for managing user-related operations. */
public interface IUserService {

    /**
     * Creates a new user.
     *
     * @param userCreateRequestDTO the user create request DTO containing the user's details
     * @return the user get response DTO
     */
    UserGetResponseDTO create(UserCreateRequestDTO userCreateRequestDTO);

    /**
     * Updates an existing user.
     *
     * @param userUpdateRequestDTO the user update request DTO containing the user's details
     * @return the user get response DTO
     */
    UserGetResponseDTO update(UserUpdateRequestDTO userUpdateRequestDTO);

    /**
     * Deletes a user.
     *
     * @param id the id of the user to delete
     */
    void delete(Long id);

    /**
     * Gets users.
     *
     * @param params the search parameters
     * @return the page of user get response DTOs
     */
    Page<UserGetResponseDTO> get(Map<String, String> params);

    /**
     * Validates the username.
     *
     * @param username the username to validate
     */
    void validateUsername(String username);

    /**
     * Checks if a user exists by username.
     *
     * @param username the username to check
     * @return true if the user exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Gets a user by ID.
     *
     * @param id the ID of the user to get
     * @return the user
     */
    User getById(Long id);
}
