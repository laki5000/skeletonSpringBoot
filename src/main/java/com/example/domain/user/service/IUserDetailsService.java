package com.example.domain.user.service;

import com.example.domain.user.dto.request.UserDetailsRequestDTO;
import com.example.domain.user.model.UserDetails;

/** Service interface for user details service. */
public interface IUserDetailsService {
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
