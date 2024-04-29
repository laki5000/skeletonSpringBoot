package com.example.user.service;

import com.example.exception.NotFoundException;
import com.example.exception.NotModifiedException;
import com.example.user.dto.response.UserGetResponse;
import com.example.user.mapper.UserMapper;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import com.example.utils.repository.BaseRepository;
import com.example.utils.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.example.utils.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
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
    public void testGetRepository() {
        // Given

        // Then
        BaseRepository<User, Long> result = userService.getRepository();

        // When
        assertNotNull(result);
        assertEquals(userRepository, result);

        verify(userService, times(1)).getRepository();
    }

    @Test
    public void testCreate() {
        // Given
        doNothing().when(userService).validateCreate(cRequest);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.toEntity(cRequest)).thenReturn(entity);
        when(userMapper.toGetResponse(entity)).thenReturn(response);

        // When
        UserGetResponse result = userService.create(cRequest);

        // Then
        assertNotNull(result);
        assertEquals(response, result);

        verify(userService, times(1)).validateCreate(cRequest);
        verify(userRepository, times(1)).save(entity);
        verify(userMapper, times(1)).toEntity(cRequest);
        verify(userMapper, times(1)).toGetResponse(entity);
    }

    @Test
    public void testUpdate() {
        // Given
        when(userService.getIdFromUpdateRequest(uRequest)).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(entity));
        doNothing().when(userService).doUpdate(entity, uRequest);
        when(userMapper.toGetResponse(any())).thenReturn(response);

        // Then
        UserGetResponse result = userService.update(uRequest);

        // When
        assertNotNull(result);
        assertEquals(response, result);

        verify(userService, times(1)).getIdFromUpdateRequest(uRequest);
        verify(userService, times(1)).validateUpdate(uRequest);
        verify(userService, times(1)).doUpdate(entity, uRequest);
        verify(userMapper, times(1)).toGetResponse(any());
    }

    @Test
    public void testDelete() {
        // Given
        doNothing().when(userService).validateDelete(id);
        doNothing().when(userRepository).deleteById(id);

        // When
        userService.delete(id);

        // Then
        verify(userService, times(1)).validateDelete(id);
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGet() {
        // Given
        Map<String, String> params = new HashMap<>();
        Page<User> page = new PageImpl<>(Collections.singletonList(entity));
        when(userRepository.findAllWithCriteria(params)).thenReturn(page);
        when(userMapper.toGetResponse(entity)).thenReturn(response);

        // When
        Page<UserGetResponse> result = userService.get(params);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(response, result.getContent().get(0));

        verify(userRepository, times(1)).findAllWithCriteria(params);
        verify(userMapper, times(1)).toGetResponse(entity);
    }

    @Test
    public void testValidateCreate() {
        // Given
        doNothing().when(userService).validateByUsername(cRequest.getUsername());

        // When
        userService.validateCreate(cRequest);

        // Then
        verify(userService, times(1)).validateByUsername(cRequest.getUsername());
    }

    @Test
    public void testValidateUpdate() {
        // Given
        when(userRepository.findById(uRequest.getId())).thenReturn(Optional.of(entity));

        // When
        User result = userService.validateUpdate(uRequest);

        // Then
        assertNotNull(result);
        assertEquals(entity, result);

        verify(userRepository, times(1)).findById(uRequest.getId());
    }

    @Test
    public void testValidateDelete() {
        // Given
        when(userRepository.findById(uRequest.getId())).thenReturn(Optional.of(entity));

        // When
        userService.validateDelete(uRequest.getId());

        // Then
        verify(userRepository, times(1)).findById(uRequest.getId());
    }

    @Test
    public void testValidateById_WithValidId_ShouldReturnUser() {
        // Given
        when(userRepository.findById(id)).thenReturn(Optional.of(entity));

        // When
        User result = userService.validateById(id);

        // Then
        assertNotNull(result);
        assertEquals(entity, result);

        verify(userRepository, times(1)).findById(id);
    }

    @Test
    public void testValidateById_WithInvalidId_ShouldThrowNotFoundException() {
        // Given
        when(userRepository.findById(invalidId)).thenReturn(Optional.empty());

        // When

        // Then
        assertThrows(NotFoundException.class, () -> userService.validateById(invalidId));

        verify(userRepository, times(1)).findById(invalidId);
    }

    @Test
    public void testValidateByUsername_WithValidUsername_ShouldDoNothing() {
        // Given
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(entity));

        // When
        userService.validateByUsername(username);

        // Then
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testValidateByUsername_WithInvalidUsername_ShouldThrowNotFoundException() {
        // Given
        when(userRepository.findByUsername(invalidUsername)).thenReturn(Optional.empty());

        // When

        // Then
        assertThrows(NotFoundException.class, () -> userService.validateByUsername(invalidUsername));

        verify(userRepository, times(1)).findByUsername(invalidUsername);
    }

    @Test
    public void testGetIdFromUpdateRequest() {
        // Given

        // When
        Long result = userService.getIdFromUpdateRequest(uRequest);

        // Then
        assertNotNull(result);
        assertEquals(id, result);
    }

    @Test
    public void testDoUpdate_PasswordChanged_ShouldSuccess() {
        // Given
        User entityCopy = entity.copy();

        // When
        userService.doUpdate(entityCopy, uRequest);

        // Then
        assertEquals(uRequest.getPassword(), entityCopy.getPassword());

        verify(userService, times(1)).doUpdate(entityCopy, uRequest);
    }

    @Test
    public void testDoUpdate_PasswordNotChanged_ShouldThrowNotModifiedException() {
        // Given

        // When
        assertThrows(NotModifiedException.class, () -> userService.doUpdate(entity, notModifiedURequest));

        // Then
        verify(userService, times(1)).doUpdate(entity, notModifiedURequest);
    }
}
