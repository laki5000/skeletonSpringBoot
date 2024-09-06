package com.example.user.controller;

import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.request.UserUpdateRequestDTO;
import com.example.user.service.IUserService;
import com.example.utils.dto.response.BaseResponseDTO;
import com.example.utils.dto.response.SuccessResponseDTO;
import com.example.utils.service.IMessageService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.Valid;
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
@SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "False positive - no mutable fields exposed")
public class UserController {
    private static final String SUCCESS_USER_CREATED = "success.user.created";
    private static final String SUCCESS_USER_UPDATED = "success.user.updated";
    private static final String SUCCESS_USER_DELETED = "success.user.deleted";
    private static final String SUCCESS_USER_GET = "success.user.get";

    private final IMessageService messageService;
    private final IUserService userService;

    /**
     * Creates a new user.
     *
     * @param userCreateRequestDTO the user create request DTO containing the user's details
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserCreateRequestDTO userCreateRequestDTO) {
        log.info("api/v1/users - Creating user");

        return ResponseEntity.status(201)
                .body(
                        SuccessResponseDTO.builder()
                                .message(messageService.getMessage(SUCCESS_USER_CREATED))
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
    public ResponseEntity<?> update(@Valid @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        log.info("api/v1/users - Updating user");

        return ResponseEntity.ok(
                SuccessResponseDTO.builder()
                        .message(messageService.getMessage(SUCCESS_USER_UPDATED))
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
        log.info("api/v1/users - Deleting user");

        userService.delete(id);

        return ResponseEntity.ok(
                BaseResponseDTO.builder()
                        .message(messageService.getMessage(SUCCESS_USER_DELETED))
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
        log.info("api/v1/users - Getting users");

        return ResponseEntity.ok(
                SuccessResponseDTO.builder()
                        .message(messageService.getMessage(SUCCESS_USER_GET))
                        .data(userService.get(params))
                        .build());
    }
}
