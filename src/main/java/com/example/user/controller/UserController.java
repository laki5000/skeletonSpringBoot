package com.example.user.controller;

import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.request.UserUpdateRequestDTO;
import com.example.user.service.IUserService;
import com.example.utils.dto.response.SuccessResponseDTO;
import com.example.utils.service.IMessageService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller class for managing user-related endpoints. */
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final IMessageService messageService;
    private final IUserService userService;

    /**
     * Creates a new user.
     *
     * @param userCreateRequestDTO the user create request DTO containing the user's details
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserCreateRequestDTO userCreateRequestDTO) {
        log.info("Creating user");

        return ResponseEntity.status(201)
                .body(
                        SuccessResponseDTO.builder()
                                .message(messageService.getMessage("success.user.created"))
                                .data(userService.create(userCreateRequestDTO))
                                .build());
    }

    /**
     * Updates an existing user.
     *
     * @param userUpdateRequestDTO the user update request DTO containing the user's details
     * @return the response entity
     */
    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        log.info("Updating user");

        return ResponseEntity.ok(
                SuccessResponseDTO.builder()
                        .message(messageService.getMessage("success.user.updated"))
                        .data(userService.update(userUpdateRequestDTO))
                        .build());
    }

    /**
     * Deletes a user.
     *
     * @param id the id of the user to delete
     * @return the response entity
     */
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id) {
        log.info("Deleting user");

        userService.delete(id);

        return ResponseEntity.ok(
                SuccessResponseDTO.builder()
                        .message(messageService.getMessage("success.user.deleted"))
                        .build());
    }

    /**
     * Gets users.
     *
     * @param params the search parameters
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<?> get(@RequestParam Map<String, String> params) {
        log.info("Getting users");

        return ResponseEntity.ok(
                SuccessResponseDTO.builder()
                        .message(messageService.getMessage("success.user.get"))
                        .data(userService.get(params))
                        .build());
    }
}
