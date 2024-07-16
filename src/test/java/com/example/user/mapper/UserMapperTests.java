package com.example.user.mapper;

import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.response.UserGetResponseDTO;
import com.example.user.model.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.example.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/** Unit tests for {@link IUserMapper}. */
public class UserMapperTests {
    private final IUserMapper userMapper = Mappers.getMapper(IUserMapper.class);

    /** Tests the successful mapping of an entity to a get response DTO. */
    @Test
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

        // When
        UserGetResponseDTO result = userMapper.toGetResponseDTO(user);

        // Then
        assertEquals(TEST_ID, result.getId());
        assertEquals(TEST_USERNAME, result.getUsername());
        assertEquals(TEST_INSTANT, result.getCreatedAt());
        assertEquals(TEST_INSTANT2, result.getUpdatedAt());
        assertEquals(TEST_USERNAME2, result.getCreatedBy());
        assertEquals(TEST_USERNAME, result.getUpdatedBy());
    }

    /** Tests the successful mapping of a create request DTO to an entity. */
    @Test
    public void toEntity_Success() {
        // Given
        UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO.builder().username(TEST_USERNAME).password(TEST_PASSWORD).build();

        // When
        User result = userMapper.toEntity(userCreateRequestDTO, TEST_USERNAME2);

        // Then
        assertEquals(TEST_USERNAME, result.getUsername());
        assertEquals(TEST_PASSWORD, result.getPassword());
        assertEquals(TEST_USERNAME2, result.getCreatedBy());
        assertNull(result.getId());
        assertNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
        assertNull(result.getUpdatedBy());
    }
}
