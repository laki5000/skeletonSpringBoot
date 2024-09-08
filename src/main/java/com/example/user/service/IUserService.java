package com.example.user.service;

import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.request.UserUpdateRequestDTO;
import com.example.user.dto.response.UserGetResponseDTO;
import com.example.utils.dto.request.FilteringDTO;
import java.util.List;
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
     * @param id the id of the user to update
     * @param userUpdateRequestDTO the user update request DTO containing the user's details
     * @return the user get response DTO
     */
    UserGetResponseDTO update(Long id, UserUpdateRequestDTO userUpdateRequestDTO);

    /**
     * Deletes a user.
     *
     * @param id the id of the user to delete
     */
    void delete(Long id);

    /**
     * Gets users.
     *
     * @param filteringDTOList the search parameters
     * @return the page of user get response DTOs
     */
    Page<UserGetResponseDTO> get(List<FilteringDTO> filteringDTOList);
}
