package com.example.domain.user.service;

import com.example.domain.user.dto.request.UserDetailsRequestDTO;
import com.example.domain.user.dto.response.UserDetailsResponseDTO;
import com.example.domain.user.model.UserDetails;

/** Service interface for user details service. */
public interface IUserDetailsService {
    /**
     * Converts a UserDetails to a UserDetailsResponseDTO.
     *
     * @param userDetails the entity to convert
     * @return the converted DTO
     */
    UserDetailsResponseDTO mapToResponseDTO(UserDetails userDetails);

    /**
     * Converts a UserDetailsRequestDTO to a UserDetails.
     *
     * @param userDetailsRequestDTO the DTO to convert
     * @param createdBy the user who created the user
     * @return the converted entity
     */
    UserDetails mapToEntity(UserDetailsRequestDTO userDetailsRequestDTO, String createdBy);

    /**
     * Updates user details.
     *
     * @param userDetails the user details to update
     * @param userDetailsRequestDTO the DTO containing the user's details
     * @return true if the user details are updated, false otherwise
     */
    boolean updateUserDetails(UserDetails userDetails, UserDetailsRequestDTO userDetailsRequestDTO);

    /**
     * Updates the audit fields.
     *
     * @param userDetails the user details entity
     * @param detailsUpdated true if the user details are updated, false otherwise
     */
    void updateAuditFields(UserDetails userDetails, boolean detailsUpdated);
}
