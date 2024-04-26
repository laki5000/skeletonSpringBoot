package com.example.user.service;

import com.example.user.dto.request.UserCreateRequest;
import com.example.user.dto.request.UserUpdateRequest;
import com.example.user.dto.response.UserGetResponse;
import com.example.user.mapper.UserMapper;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import com.example.utils.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.user.UserTestUtils.*;
import static com.example.utils.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private MessageService messageService;
    private final UserCreateRequest cRequest = getUserCreateRequest(username, password);
    private final UserUpdateRequest uRequest = getUserUpdateRequest(id, modifiedPassword);
    private final User entity = getUser(id, username, password, null, username, null, null);
    private final User uEntity = getUser(id, username, modifiedPassword, null, username, null, null);
    private final UserGetResponse response = getUserGetResponse(1L, username, null, null, username, null);

    @BeforeEach
    public void setup() {
        userService = spy(new UserService(messageService, userRepository, userMapper));
    }

    @Test
    public void testCreateUser() {
        // Arrange
        doNothing().when(userService).validateCreate(cRequest);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.toEntity(cRequest)).thenReturn(entity);
        when(userMapper.toGetResponse(entity)).thenReturn(response);

        // Act
        UserGetResponse result = userService.create(cRequest);

        // Assert
        assertNotNull(result);
        assertEquals(response, result);

        verify(userService, times(1)).validateCreate(cRequest);
        verify(userRepository, times(1)).save(entity);
        verify(userMapper, times(1)).toEntity(cRequest);
        verify(userMapper, times(1)).toGetResponse(entity);
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        when(userService.getIdFromUpdateRequest(uRequest)).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(entity));
        doNothing().when(userService).doUpdate(entity, uRequest);
        when(userMapper.toGetResponse(any())).thenReturn(response);

        // Act
        UserGetResponse result = userService.update(uRequest);

        // Assert
        assertNotNull(result);
        assertEquals(response, result);

        verify(userService, times(1)).getIdFromUpdateRequest(uRequest);
        verify(userService, times(1)).validateUpdate(uRequest);
        verify(userService, times(1)).doUpdate(entity, uRequest);
        verify(userMapper, times(1)).toGetResponse(any());
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        doNothing().when(userService).validateDelete(id);
        doNothing().when(userRepository).deleteById(id);

        // Act
        userService.delete(id);

        // Assert
        verify(userService, times(1)).validateDelete(id);
        verify(userRepository, times(1)).deleteById(id);
    }
}
