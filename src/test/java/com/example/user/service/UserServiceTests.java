package com.example.user.service;

import static com.example.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.exception.ConflictException;
import com.example.exception.NotFoundException;
import com.example.exception.NotModifiedException;
import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.request.UserUpdateRequestDTO;
import com.example.user.dto.response.UserGetResponseDTO;
import com.example.user.mapper.IUserMapper;
import com.example.user.model.User;
import com.example.user.repository.IUserRepository;
import com.example.utils.service.IMessageService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/** Unit tests for {@link UserService}. */
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @InjectMocks private UserService userService;
    @Mock private IMessageService messageService;
    @Mock private IUserRepository userRepository;
    @Mock private IUserMapper userMapper;

    /** Tests the successful creation of a user. */
    @Test
    void create_Success() {
        // Given
        UserCreateRequestDTO userCreateRequestDTO =
                UserCreateRequestDTO.builder().username(TEST_USERNAME).build();
        User user = User.builder().build();
        UserGetResponseDTO userGetResponseDTO = UserGetResponseDTO.builder().build();

        when(userRepository.existsByUsername(TEST_USERNAME)).thenReturn(false);
        when(userMapper.toEntity(userCreateRequestDTO, "unknown")).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toGetResponseDTO(user)).thenReturn(userGetResponseDTO);

        // When
        UserGetResponseDTO result = userService.create(userCreateRequestDTO);

        // Then
        assertEquals(userGetResponseDTO, result);

        verify(userRepository).existsByUsername(TEST_USERNAME);
        verify(userMapper).toEntity(userCreateRequestDTO, "unknown");
        verify(userRepository).save(user);
        verify(userMapper).toGetResponseDTO(user);
    }

    /** Tests the successful update of a user. */
    @Test
    void update_Success() {
        // Given
        UserUpdateRequestDTO userUpdateRequestDTO =
                UserUpdateRequestDTO.builder().id(TEST_ID).password(TEST_PASSWORD2).build();
        User user = User.builder().password(TEST_PASSWORD).build();
        UserGetResponseDTO userGetResponseDTO = UserGetResponseDTO.builder().build();

        when(userRepository.findById(TEST_ID)).thenReturn(java.util.Optional.of(user));
        when(userMapper.toGetResponseDTO(user)).thenReturn(userGetResponseDTO);
        when(userRepository.saveAndFlush(user)).thenReturn(user);

        // When
        UserGetResponseDTO result = userService.update(userUpdateRequestDTO);

        // Then
        assertEquals(userGetResponseDTO, result);

        verify(userRepository).findById(TEST_ID);
        verify(userMapper).toGetResponseDTO(user);
        verify(userRepository).saveAndFlush(user);
    }

    /** Tests the unsuccessful update of a user due to not modified. */
    @Test
    void update_NotModified() {
        // Given
        UserUpdateRequestDTO userUpdateRequestDTO =
                UserUpdateRequestDTO.builder().id(TEST_ID).password(TEST_PASSWORD).build();
        User user = User.builder().password(TEST_PASSWORD).build();

        when(userRepository.findById(TEST_ID)).thenReturn(java.util.Optional.of(user));

        // When & Then
        assertThrows(NotModifiedException.class, () -> userService.update(userUpdateRequestDTO));

        verify(userRepository).findById(TEST_ID);
    }

    /** Tests the successful deletion of a user. */
    @Test
    void delete_Success() {
        // Given
        User user = User.builder().build();

        when(userRepository.findById(TEST_ID)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        // When
        userService.delete(TEST_ID);

        // Then
        verify(userRepository).findById(TEST_ID);
        verify(userRepository).delete(user);
    }

    /** Tests the successful retrieval of users. */
    @Test
    void get_Success() {
        // Given
        Map<String, String> params = Map.of(TEST_KEY, TEST_VALUE);
        User user = User.builder().build();
        UserGetResponseDTO userGetResponseDTO = UserGetResponseDTO.builder().build();

        when(userRepository.findAllWithCriteria(params)).thenReturn(new PageImpl<>(List.of(user)));
        when(userMapper.toGetResponseDTO(user)).thenReturn(userGetResponseDTO);

        // When
        Page<UserGetResponseDTO> result = userService.get(params);

        // Then
        assertEquals(List.of(userGetResponseDTO), result.getContent());

        verify(userRepository).findAllWithCriteria(params);
        verify(userMapper).toGetResponseDTO(user);
    }

    /** Tests the successful validation of a unique username. */
    @Test
    void ensureUsernameIsUnique_Success() {
        // Given
        when(userRepository.existsByUsername(TEST_USERNAME)).thenReturn(false);

        // When
        userService.ensureUsernameIsUnique(TEST_USERNAME);

        // Then
        verify(userRepository).existsByUsername(TEST_USERNAME);
    }

    /** Tests the unsuccessful validation of a unique username due to conflict. */
    @Test
    void ensureUsernameIsUnique_UsernameExists() {
        // Given
        when(userRepository.existsByUsername(TEST_USERNAME)).thenReturn(true);

        // When & Then
        assertThrows(
                ConflictException.class, () -> userService.ensureUsernameIsUnique(TEST_USERNAME));

        verify(userRepository).existsByUsername(TEST_USERNAME);
    }

    /** Tests the successful check for the existence of a user by username. */
    @Test
    void existsByUsername_Success() {
        // Given
        when(userRepository.existsByUsername(TEST_USERNAME)).thenReturn(true);

        // When
        boolean result = userService.existsByUsername(TEST_USERNAME);

        // Then
        assertTrue(result);

        verify(userRepository).existsByUsername(TEST_USERNAME);
    }

    /** Tests the successful retrieval of a user by ID. */
    @Test
    void getById_Success() {
        // Given
        User user = User.builder().build();

        when(userRepository.findById(TEST_ID)).thenReturn(Optional.of(user));

        // When
        User result = userService.getById(TEST_ID);

        // Then
        assertEquals(user, result);

        verify(userRepository).findById(TEST_ID);
    }

    /** Tests the unsuccessful retrieval of a user by ID due to not found. */
    @Test
    void getById_NotFound() {
        // Given
        when(userRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> userService.getById(TEST_ID));

        verify(userRepository).findById(TEST_ID);
    }
}
