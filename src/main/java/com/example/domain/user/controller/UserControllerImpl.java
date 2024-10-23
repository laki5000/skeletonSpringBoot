package com.example.domain.user.controller;

import static com.example.constants.Constants.*;
import static org.springframework.http.HttpStatus.CREATED;

import com.example.base.dto.response.BaseResponseDTO;
import com.example.domain.user.dto.request.UserCreateRequestDTO;
import com.example.domain.user.dto.request.UserUpdateRequestDTO;
import com.example.domain.user.service.IUserService;
import com.example.utils.dto.request.FilteringDTO;
import com.example.utils.dto.response.SuccessResponseDTO;
import com.example.utils.service.IMessageService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller class for managing user-related endpoints. */
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(USER_BASE_URL)
@SuppressFBWarnings(value = "EI_EXPOSE_REP2")
public class UserControllerImpl implements IUserController {
    private final IMessageService messageService;
    private final IUserService userService;

    public static final String SUCCESS_USER_CREATED = "success.user.created";
    public static final String SUCCESS_USER_UPDATED = "success.user.updated";
    public static final String SUCCESS_USER_DELETED = "success.user.deleted";
    public static final String SUCCESS_USER_GET = "success.user.get";

    /**
     * Creates a new user.
     *
     * @param userCreateRequestDTO the DTO containing the user's details
     * @return the response entity
     */
    @Override
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserCreateRequestDTO userCreateRequestDTO) {
        log.info("create called");

        return ResponseEntity.status(CREATED)
                .body(
                        SuccessResponseDTO.builder()
                                .message(messageService.getMessage(SUCCESS_USER_CREATED))
                                .data(userService.create(userCreateRequestDTO))
                                .build());
    }

    /**
     * Gets users.
     *
     * @param page the page number
     * @param limit the number of users per page
     * @param orderBy the field to order by
     * @param orderDirection the direction to order by
     * @param filteringDTOList the search parameters
     * @return the response entity
     */
    @Override
    @PostMapping(GET)
    public ResponseEntity<?> get(
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(required = false, defaultValue = DEFAULT_LIMIT) int limit,
            @RequestParam(required = false, defaultValue = DEFAULT_ORDER_BY) String orderBy,
            @RequestParam(required = false, defaultValue = DEFAULT_ORDER_DIRECTION)
                    String orderDirection,
            @RequestBody(required = false) List<FilteringDTO> filteringDTOList) {
        log.info("get called");

        return ResponseEntity.ok(
                SuccessResponseDTO.builder()
                        .message(messageService.getMessage(SUCCESS_USER_GET))
                        .data(
                                userService.get(
                                        page, limit, orderBy, orderDirection, filteringDTOList))
                        .build());
    }

    /**
     * Updates an existing user.
     *
     * @param id the id of the user to update
     * @param userUpdateRequestDTO the DTO containing the user's details
     * @return the response entity
     */
    @Override
    @PatchMapping(BY_ID)
    public ResponseEntity<?> update(
            @PathVariable Long id, @Valid @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        log.info("update called");

        return ResponseEntity.ok(
                SuccessResponseDTO.builder()
                        .message(messageService.getMessage(SUCCESS_USER_UPDATED))
                        .data(userService.update(id, userUpdateRequestDTO))
                        .build());
    }

    /**
     * Deletes a user.
     *
     * @param id the id of the user to delete
     * @return the response entity
     */
    @Override
    @DeleteMapping(BY_ID)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info("delete called");

        userService.delete(id);

        return ResponseEntity.ok(
                BaseResponseDTO.builder()
                        .message(messageService.getMessage(SUCCESS_USER_DELETED))
                        .build());
    }
}
