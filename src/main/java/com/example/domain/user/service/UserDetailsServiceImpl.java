package com.example.domain.user.service;

import com.example.domain.user.dto.request.UserDetailsRequestDTO;
import com.example.domain.user.dto.response.UserDetailsResponseDTO;
import com.example.domain.user.mapper.IUserDetailsMapper;
import com.example.domain.user.model.UserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/** A service class for managing user details-related operations. */
@Log4j2
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements IUserDetailsService {
    private final IUserDetailsMapper userDetailsMapper;

    /**
     * Converts a UserDetails to a UserDetailsResponseDTO.
     *
     * @param userDetails the entity to convert
     * @return the converted DTO
     */
    @Override
    public UserDetailsResponseDTO mapToResponseDTO(UserDetails userDetails) {
        log.debug("Mapping UserDetails to UserDetailsResponseDTO");

        return userDetailsMapper.toResponseDTO(userDetails);
    }

    /**
     * Converts a UserDetailsRequestDTO to a UserDetails.
     *
     * @param userDetailsRequestDTO the DTO to convert
     * @param createdBy the user who created the user
     * @return the converted entity
     */
    @Override
    public UserDetails mapToEntity(UserDetailsRequestDTO userDetailsRequestDTO, String createdBy) {
        log.debug("Mapping UserDetailsRequestDTO to UserDetails");

        return userDetailsMapper.toEntity(userDetailsRequestDTO, createdBy);
    }

    /**
     * Updates user details.
     *
     * @param userDetails the user details to update
     * @param userDetailsRequestDTO the DTO containing the user's details
     * @return true if the user details are updated, false otherwise
     */
    @Override
    public boolean updateUserDetails(
            UserDetails userDetails, UserDetailsRequestDTO userDetailsRequestDTO) {
        log.debug("Updating user details with id: {}", userDetails.getId());

        boolean updated = false;

        if (userDetailsRequestDTO != null) {
            if (userDetailsRequestDTO.getFirstName() != null
                    && !userDetails.getFirstName().equals(userDetailsRequestDTO.getFirstName())) {
                userDetails.setFirstName(userDetailsRequestDTO.getFirstName());
                updated = true;
            }

            if (userDetailsRequestDTO.getLastName() != null
                    && !userDetails.getLastName().equals(userDetailsRequestDTO.getLastName())) {
                userDetails.setLastName(userDetailsRequestDTO.getLastName());
                updated = true;
            }
        }

        return updated;
    }

    /**
     * Updates the audit fields.
     *
     * @param userDetails the user details entity
     * @param detailsUpdated true if the user details are updated, false otherwise
     */
    @Override
    public void updateAuditFields(UserDetails userDetails, boolean detailsUpdated) {
        log.debug("Updating audit fields");

        if (detailsUpdated) {
            userDetails.setUpdatedBy("unknown");
        }
    }
}
