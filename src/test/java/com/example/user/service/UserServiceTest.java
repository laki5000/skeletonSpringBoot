package com.example.user.service;

import com.example.exception.ConflictException;
import com.example.exception.NotFoundException;
import com.example.exception.NotModifiedException;
import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.request.UserUpdateRequestDTO;
import com.example.user.dto.response.UserGetResponseDTO;
import com.example.user.mapper.UserMapper;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import com.example.utils.service.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.example.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private MessageService messageService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;


    @Test
    public void testCreate() {
        // Given
        UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO.builder().username(USERNAME).password(PASSWORD).build();
        User user = User.builder().username(USERNAME).password(PASSWORD).build();
        UserGetResponseDTO userGetResponseDTO = new UserGetResponseDTO(ID, USERNAME, null, null, null, null);

        when(userRepository.existsByUsername(userCreateRequestDTO.getUsername())).thenReturn(false);
        when(userMapper.toEntity(userCreateRequestDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toGetResponse(user)).thenReturn(userGetResponseDTO);

        // When
        UserGetResponseDTO result = userService.create(userCreateRequestDTO);

        // Then
        assertNotNull(result);
        assertEquals(userGetResponseDTO, result);

        verify(userRepository, times(1)).existsByUsername(userCreateRequestDTO.getUsername());
        verify(userMapper, times(1)).toEntity(userCreateRequestDTO);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toGetResponse(user);
    }

    @Test
    public void testUpdate() {
        // Given
        UserUpdateRequestDTO userUpdateRequestDTO = UserUpdateRequestDTO.builder().id(ID).password(MODIFIED_PASSWORD).build();
        User user = User.builder().id(ID).username(USERNAME).password(PASSWORD).build();
        UserGetResponseDTO userGetResponseDTO = new UserGetResponseDTO(ID, USERNAME, null, null, null, null);

        when(userRepository.findById(userUpdateRequestDTO.getId())).thenReturn(Optional.of(user));
        when(userMapper.toGetResponse(any())).thenReturn(userGetResponseDTO);

        // When
        UserGetResponseDTO result = userService.update(userUpdateRequestDTO);

        // Then
        assertNotNull(result);
        assertEquals(userGetResponseDTO, result);

        verify(userRepository, times(1)).findById(userUpdateRequestDTO.getId());
        verify(userMapper, times(1)).toGetResponse(any());
    }

    @Test
    public void testDelete() {
        // Given
        User user = User.builder().id(ID).build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(user.getId());

        // When
        userService.delete(user.getId());

        // Then
        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void testGet() {
        // Given
        User user = User.builder().id(ID).username(USERNAME).build();
        Map<String, String> params = new HashMap<>();
        Page<User> pageUser = new PageImpl<>(Collections.singletonList(user));
        UserGetResponseDTO userGetResponseDTO = new UserGetResponseDTO(ID, USERNAME, null, null, null, null);

        when(userRepository.findAllWithCriteria(params)).thenReturn(pageUser);
        when(userMapper.toGetResponse(user)).thenReturn(userGetResponseDTO);

        // When
        Page<UserGetResponseDTO> result = userService.get(params);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(userGetResponseDTO, result.getContent().get(0));

        verify(userRepository, times(1)).findAllWithCriteria(params);
        verify(userMapper, times(1)).toGetResponse(user);
    }

    @Test
    public void testValidateCreate() {
        // Given
        UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO.builder().username(USERNAME).password(PASSWORD).build();

        when(userRepository.existsByUsername(userCreateRequestDTO.getUsername())).thenReturn(false);

        // When
        userService.validateCreate(userCreateRequestDTO);

        // Then
        verify(userRepository, times(1)).existsByUsername(userCreateRequestDTO.getUsername());
    }

    @Test
    public void testValidateUpdate() {
        // Given
        UserUpdateRequestDTO userUpdateRequestDTO = UserUpdateRequestDTO.builder().id(ID).build();
        User user = User.builder().id(ID).build();

        when(userRepository.findById(userUpdateRequestDTO.getId())).thenReturn(Optional.of(user));

        // When
        User result = userService.validateUpdate(userUpdateRequestDTO);

        // Then
        assertNotNull(result);
        assertEquals(user, result);

        verify(userRepository, times(1)).findById(userUpdateRequestDTO.getId());
    }

    @Test
    public void testValidateDelete() {
        // Given
        User user = User.builder().id(ID).build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // When
        userService.validateDelete(user.getId());

        // Then
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    public void testFindById_WithValidId_ShouldReturnUser() {
        // Given
        User user = User.builder().id(ID).build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // When
        User result = userService.findById(user.getId());

        // Then
        assertNotNull(result);
        assertEquals(user, result);

        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    public void testFindById_WithInvalidId_ShouldThrowNotFoundException() {
        // Given
        when(userRepository.findById(ID)).thenThrow(NotFoundException.class);

        // When & Then
        assertThrows(NotFoundException.class, () -> userService.findById(ID));

        verify(userRepository, times(1)).findById(ID);
    }

    @Test
    public void testExistsByUsername_WithNonExistingUsername_ShouldDoNothing() {
        // Given
        when(userRepository.existsByUsername(USERNAME)).thenReturn(false);

        // When
        userService.existsByUsername(USERNAME);

        // Then
        verify(userRepository, times(1)).existsByUsername(USERNAME);
    }

    @Test
    public void testExistsByUsername_WithExistingUsername_ShouldThrowConflictException() {
        // Given
        when(userRepository.existsByUsername(USERNAME)).thenThrow(ConflictException.class);

        // When & Then
        assertThrows(ConflictException.class, () -> userService.existsByUsername(USERNAME));

        verify(userRepository, times(1)).existsByUsername(USERNAME);
    }

    @Test
    public void testGetIdFromUpdateRequest() {
        // Given
        UserUpdateRequestDTO userUpdateRequestDTO = UserUpdateRequestDTO.builder().id(ID).build();

        // When
        Long result = userService.getIdFromUpdateRequest(userUpdateRequestDTO);

        // Then
        assertNotNull(result);
        assertEquals(userUpdateRequestDTO.getId(), result);
    }

    @Test
    public void testDoUpdate_PasswordChanged_ShouldSuccess() {
        // Given
        User user = User.builder().id(ID).username(USERNAME).password(PASSWORD).build();
        UserUpdateRequestDTO userUpdateRequestDTO = UserUpdateRequestDTO.builder().id(ID).password(MODIFIED_PASSWORD).build();

        // When
        userService.doUpdate(user, userUpdateRequestDTO);

        // Then
        assertEquals(userUpdateRequestDTO.getPassword(), user.getPassword());
    }

    @Test
    public void testDoUpdate_PasswordNotChanged_ShouldThrowNotModifiedException() {
        // Given
        User user = User.builder().id(ID).username(USERNAME).password(PASSWORD).build();
        UserUpdateRequestDTO userUpdateRequestDTO = UserUpdateRequestDTO.builder().id(ID).password(PASSWORD).build();

        // When & Then
        assertThrows(NotModifiedException.class, () -> userService.doUpdate(user, userUpdateRequestDTO));
    }
}
