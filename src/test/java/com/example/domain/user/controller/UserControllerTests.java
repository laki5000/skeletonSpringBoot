package com.example.domain.user.controller;

import static com.example.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.example.domain.user.dto.request.UserCreateRequestDTO;
import com.example.domain.user.dto.request.UserUpdateRequestDTO;
import com.example.domain.user.dto.response.UserResponseDTO;
import com.example.domain.user.service.IUserService;
import com.example.utils.service.IMessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    @InjectMocks private UserControllerImpl userController;
    @Mock private IMessageService messageService;
    @Mock private IUserService userService;

    @Test
    @DisplayName("Tests the successful call of the create method.")
    public void create_Success() {
        // Given
        UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO.builder().build();
        UserResponseDTO userResponseDTO = UserResponseDTO.builder().build();

        when(userService.create(userCreateRequestDTO)).thenReturn(userResponseDTO);

        // When
        ResponseEntity<?> result = userController.create(userCreateRequestDTO);

        // Then
        assertEquals(CREATED, result.getStatusCode());
    }

    @Test
    @DisplayName("Tests the successful call of the get method.")
    public void get_Success() {
        // Given
        when(userService.get(TEST_PAGE, TEST_LIMIT, null, null, null)).thenReturn(null);

        // When
        ResponseEntity<?> result = userController.get(TEST_PAGE, TEST_LIMIT, null, null, null);

        // Then
        assertEquals(OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Tests the successful call of the update method.")
    public void update_Success() {
        // Given
        UserUpdateRequestDTO userUpdateRequestDTO = UserUpdateRequestDTO.builder().build();

        when(userService.update(TEST_ID, userUpdateRequestDTO)).thenReturn(null);

        // When
        ResponseEntity<?> result = userController.update(TEST_ID, userUpdateRequestDTO);

        // Then
        assertEquals(OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Tests the successful call of the delete method.")
    public void delete_Success() {
        // Given
        doNothing().when(userService).delete(TEST_ID);

        // When
        ResponseEntity<?> result = userController.delete(TEST_ID);

        // Then
        assertEquals(OK, result.getStatusCode());
    }
}
