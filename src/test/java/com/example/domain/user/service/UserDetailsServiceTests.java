package com.example.domain.user.service;

import static com.example.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.domain.user.dto.request.UserDetailsRequestDTO;
import com.example.domain.user.model.UserDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for {@link UserDetailsServiceImpl}. */
@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTests {
    @InjectMocks private UserDetailsServiceImpl userDetailsService;

    @Test
    @DisplayName("Tests the successful update of user details.")
    public void updateUserDetails_Success() {
        // Given
        UserDetails userDetails =
                UserDetails.builder().firstName(TEST_FIRST_NAME).lastName(TEST_LAST_NAME).build();
        UserDetailsRequestDTO userDetailsRequestDTO =
                UserDetailsRequestDTO.builder().firstName(TEST_FIRST_NAME2).build();

        // When
        boolean result = userDetailsService.updateUserDetails(userDetails, userDetailsRequestDTO);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Tests the successful update of audit fields.")
    public void updateAuditFields_Success() {
        // Given
        UserDetails userDetails = new UserDetails();

        // When
        userDetailsService.updateAuditFields(userDetails, true);

        // Then
        assertEquals("unknown", userDetails.getUpdatedBy());
    }
}
