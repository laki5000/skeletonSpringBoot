package com.example.domain.user.service;

import static com.example.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.domain.user.dto.request.UserDetailsRequestDTO;
import com.example.domain.user.dto.response.UserDetailsResponseDTO;
import com.example.domain.user.mapper.IUserDetailsMapper;
import com.example.domain.user.model.UserDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for {@link UserDetailsServiceImpl}. */
@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTests {
    @InjectMocks private UserDetailsServiceImpl userDetailsService;
    @Mock private IUserDetailsMapper userDetailsMapper;

    @Test
    @DisplayName("Tests the successful conversion of a UserDetails to a UserDetailsResponseDTO.")
    public void mapToResponseDTO_Success() {
        // Given
        UserDetails userDetails = new UserDetails();
        UserDetailsResponseDTO userDetailsResponseDTO = UserDetailsResponseDTO.builder().build();

        when(userDetailsMapper.toResponseDTO(userDetails)).thenReturn(userDetailsResponseDTO);

        // When
        UserDetailsResponseDTO result = userDetailsService.mapToResponseDTO(userDetails);

        // Then
        assertEquals(userDetailsResponseDTO, result);

        verify(userDetailsMapper).toResponseDTO(userDetails);
    }

    @Test
    @DisplayName("Tests the successful conversion of a UserDetailsRequestDTO to a UserDetails.")
    public void mapToEntity_Success() {
        // Given
        UserDetailsRequestDTO userDetailsRequestDTO = UserDetailsRequestDTO.builder().build();
        UserDetails userDetails = new UserDetails();

        when(userDetailsMapper.toEntity(userDetailsRequestDTO, TEST_USERNAME))
                .thenReturn(userDetails);

        // When
        UserDetails result = userDetailsService.mapToEntity(userDetailsRequestDTO, TEST_USERNAME);

        // Then
        assertEquals(userDetails, result);

        verify(userDetailsMapper).toEntity(userDetailsRequestDTO, TEST_USERNAME);
    }

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
