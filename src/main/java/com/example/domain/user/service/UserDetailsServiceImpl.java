package com.example.domain.user.service;

import com.example.domain.user.dto.request.UserDetailsRequestDTO;
import com.example.domain.user.model.UserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/** A service class for managing user details-related operations. */
@Log4j2
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements IUserDetailsService {

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
        log.debug("updateUserDetails called");

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
        log.debug("updateAuditFields called");

        if (detailsUpdated) {
            userDetails.setUpdatedBy("unknown");
        }
    }
}
