package com.example.domain.user.controller;

import static com.example.utils.constants.EndpointConstants.*;
import static com.example.utils.constants.FilteringConstants.*;
import static com.example.utils.constants.MessageConstants.*;
import static com.example.utils.constants.SuppressionConstants.EI_EXPOSE_REP2;
import static com.example.utils.constants.SuppressionConstants.EI_EXPOSE_REP2_JUSTIFICATION;
import static org.springframework.http.HttpStatus.CREATED;

import com.example.domain.user.dto.request.UserCreateRequestDTO;
import com.example.domain.user.dto.request.UserUpdateRequestDTO;
import com.example.domain.user.service.IUserService;
import com.example.utils.dto.request.FilteringDTO;
import com.example.utils.dto.response.BaseResponseDTO;
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
@SuppressFBWarnings(value = EI_EXPOSE_REP2, justification = EI_EXPOSE_REP2_JUSTIFICATION)
public class UserControllerImpl implements IUserController {
    private final IMessageService messageService;
    private final IUserService userService;

    /**
     * Creates a new user.
     *
     * @param userCreateRequestDTO the DTO containing the user's details
     * @return the response entity
     */
    @Override
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserCreateRequestDTO userCreateRequestDTO) {
        log.info(USER_BASE_URL + " - Creating user");

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
    @PostMapping(GET_PATH)
    public ResponseEntity<?> get(
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(required = false, defaultValue = DEFAULT_LIMIT) int limit,
            @RequestParam(required = false, defaultValue = FIELD_ID) String orderBy,
            @RequestParam(required = false, defaultValue = ASC) String orderDirection,
            @RequestBody(required = false) List<FilteringDTO> filteringDTOList) {
        log.info(USER_BASE_URL + GET_PATH + " - Getting users");

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
    @PatchMapping(ID_PATH)
    public ResponseEntity<?> update(
            @PathVariable Long id, @Valid @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        log.info(USER_BASE_URL + "/{} - Updating user", id);

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
    @DeleteMapping(ID_PATH)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info(USER_BASE_URL + "/{} - Deleting user", id);

        userService.delete(id);

        return ResponseEntity.ok(
                BaseResponseDTO.builder()
                        .message(messageService.getMessage(SUCCESS_USER_DELETED))
                        .build());
    }
}
