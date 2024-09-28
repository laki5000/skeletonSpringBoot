package com.example.domain.user.mapper;

import static com.example.Constants.*;
import static com.example.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.domain.user.dto.request.UserDetailsRequestDTO;
import com.example.domain.user.dto.response.UserDetailsResponseDTO;
import com.example.domain.user.model.UserDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class UserDetailsMapperTests {
    private final IUserDetailsMapper userDetailsMapper =
            Mappers.getMapper(IUserDetailsMapper.class);

    @Test
    @DisplayName("Tests the successful mappign of UserDetails to UserDetailsDTO")
    public void toResponseDTO_Success() {
        // Given
        UserDetails userDetails =
                buildUserDetails(
                        TEST_ID,
                        TEST_FIRST_NAME,
                        TEST_LAST_NAME,
                        TEST_INSTANT,
                        TEST_INSTANT2,
                        TEST_USERNAME2,
                        TEST_USERNAME);
        UserDetailsResponseDTO expected =
                buildUserDetailsResponseDTO(
                        TEST_ID,
                        TEST_FIRST_NAME,
                        TEST_LAST_NAME,
                        TEST_INSTANT,
                        TEST_INSTANT2,
                        TEST_USERNAME2,
                        TEST_USERNAME);

        // When
        UserDetailsResponseDTO result = userDetailsMapper.toResponseDTO(userDetails);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Tests the successful mappign of UserDetailsRequestDTO to UserDetails")
    public void toEntity_Success() {
        // Given
        UserDetailsRequestDTO userDetailsRequestDTO =
                buildUserDetailsRequestDTO(TEST_FIRST_NAME, TEST_LAST_NAME);
        UserDetails expected =
                buildUserDetails(
                        null, TEST_FIRST_NAME, TEST_LAST_NAME, null, null, TEST_USERNAME, null);

        // When
        UserDetails result = userDetailsMapper.toEntity(userDetailsRequestDTO, TEST_USERNAME);

        // Then
        assertEquals(expected, result);
    }
}
