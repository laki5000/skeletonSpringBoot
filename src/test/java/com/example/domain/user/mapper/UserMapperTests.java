package com.example.domain.user.mapper;

import static com.example.Constants.*;
import static com.example.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.domain.user.dto.request.UserCreateRequestDTO;
import com.example.domain.user.dto.response.UserResponseDTO;
import com.example.domain.user.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/** Unit tests for {@link IUserMapper}. */
public class UserMapperTests {
    private final IUserMapper userMapper = Mappers.getMapper(IUserMapper.class);

    @Test
    @DisplayName("Tests the successful mapping of an User to a UserResponseDTO.")
    public void toResponseDTO_Success() {
        // Given
        User user =
                buildUser(
                        TEST_ID,
                        TEST_USERNAME,
                        TEST_PASSWORD,
                        TEST_INSTANT,
                        TEST_INSTANT2,
                        TEST_USERNAME2,
                        TEST_USERNAME,
                        TEST_ID,
                        TEST_FIRST_NAME,
                        TEST_LAST_NAME,
                        TEST_INSTANT,
                        TEST_INSTANT2,
                        TEST_USERNAME2,
                        TEST_USERNAME);
        UserResponseDTO expected =
                buildUserResponseDTO(
                        TEST_ID,
                        TEST_USERNAME,
                        TEST_INSTANT,
                        TEST_INSTANT2,
                        TEST_USERNAME2,
                        TEST_USERNAME,
                        TEST_ID,
                        TEST_FIRST_NAME,
                        TEST_LAST_NAME,
                        TEST_INSTANT,
                        TEST_INSTANT2,
                        TEST_USERNAME2,
                        TEST_USERNAME);

        // When
        UserResponseDTO result = userMapper.toResponseDTO(user);

        // Then
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Tests the unsuccessful mapping of a null User to a UserResponseDTO.")
    public void toResponseDTO_Null() {
        // Given

        // When
        UserResponseDTO result = userMapper.toResponseDTO(null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Tests the successful mapping of a UserCreateRequestDTO to a User.")
    public void toEntity_Success() {
        // Given
        UserCreateRequestDTO userCreateRequestDTO =
                buildUserCreateRequestDTO(
                        TEST_USERNAME, TEST_PASSWORD, TEST_FIRST_NAME, TEST_LAST_NAME);
        User expected =
                buildUser(
                        null,
                        TEST_USERNAME,
                        TEST_PASSWORD,
                        null,
                        null,
                        TEST_USERNAME2,
                        null,
                        null,
                        TEST_FIRST_NAME,
                        TEST_LAST_NAME,
                        null,
                        null,
                        TEST_USERNAME2,
                        null);

        // When
        User result = userMapper.toEntity(userCreateRequestDTO, TEST_USERNAME2);

        // Then
        assertEquals(expected, result);
    }
}
