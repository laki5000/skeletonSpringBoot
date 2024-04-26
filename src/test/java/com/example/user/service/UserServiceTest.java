package com.example.user.service;

import com.example.user.dto.request.UserCreateRequest;
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

    @BeforeEach
    public void setup() {
        userService = spy(new UserService(messageService, userRepository, userMapper));
    }

    @Test
    public void testCreateUser() {
        // Arrange
        UserCreateRequest request = getUserCreateRequest(username, password);
        User entity = getUser(id, username, password, null, username, null, null);
        UserGetResponse expectedResponse = getUserGetResponse(1L, username, null, null, username, null);

        doNothing().when(userService).validateCreate(request);
        when(userRepository.save(any())).thenReturn(entity);
        when(userMapper.toEntity(request)).thenReturn(entity);
        when(userMapper.toGetResponse(entity)).thenReturn(expectedResponse);

        // Act
        UserGetResponse response = userService.create(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(userService, times(1)).validateCreate(request);
        verify(userRepository, times(1)).save(any());
        verify(userMapper, times(1)).toEntity(request);
        verify(userMapper, times(1)).toGetResponse(entity);
    }
}
