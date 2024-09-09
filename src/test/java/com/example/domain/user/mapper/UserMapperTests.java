package com.example.domain.user.mapper;

import static com.example.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.domain.user.dto.request.UserCreateRequestDTO;
import com.example.domain.user.dto.response.UserGetResponseDTO;
import com.example.domain.user.mapper.IUserMapper;
import com.example.domain.user.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/** Unit tests for {@link IUserMapper}. */
public class UserMapperTests {
    private final IUserMapper userMapper = Mappers.getMapper(IUserMapper.class);

    @Test
    @DisplayName("Tests the successful mapping of an entity to a get response DTO.")
    public void toGetResponseDTO_Success() {
        // Given
        User user =
                User.builder()
                        .id(TEST_ID)
                        .username(TEST_USERNAME)
                        .password(TEST_PASSWORD)
                        .createdAt(TEST_INSTANT)
                        .updatedAt(TEST_INSTANT2)
                        .createdBy(TEST_USERNAME2)
                        .updatedBy(TEST_USERNAME)
                        .build();
        UserGetResponseDTO expected =
                UserGetResponseDTO.builder()
                        .id(TEST_ID)
                        .username(TEST_USERNAME)
                        .createdAt(TEST_INSTANT)
                        .updatedAt(TEST_INSTANT2)
                        .createdBy(TEST_USERNAME2)
                        .updatedBy(TEST_USERNAME)
                        .build();

        // When
        UserGetResponseDTO result = userMapper.toGetResponseDTO(user);

        // Then
        assertEquals(result, expected);
    }

    @Test
    @DisplayName("Tests the successful mapping of a create request DTO to an entity.")
    public void toEntity_Success() {
        // Given
        UserCreateRequestDTO userCreateRequestDTO =
                UserCreateRequestDTO.builder()
                        .username(TEST_USERNAME)
                        .password(TEST_PASSWORD)
                        .build();
        User expected =
                User.builder()
                        .username(TEST_USERNAME)
                        .password(TEST_PASSWORD)
                        .createdBy(TEST_USERNAME2)
                        .build();

        // When
        User result = userMapper.toEntity(userCreateRequestDTO, TEST_USERNAME2);

        // Then
        assertEquals(expected, result);
    }
}
