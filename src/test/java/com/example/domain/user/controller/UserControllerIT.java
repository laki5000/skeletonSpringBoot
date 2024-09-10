package com.example.domain.user.controller;

import static com.example.Constants.*;
import static com.example.utils.constants.EndpointConstants.GET_PATH;
import static com.example.utils.constants.EndpointConstants.USER_BASE_URL;
import static com.example.utils.constants.FilteringConstants.FIELD_ID;
import static com.example.utils.enums.FilterOperator.EQUALS;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import com.example.BaseIT;
import com.example.domain.user.dto.request.UserCreateRequestDTO;
import com.example.domain.user.dto.request.UserUpdateRequestDTO;
import com.example.domain.user.dto.response.UserGetResponseDTO;
import com.example.domain.user.model.User;
import com.example.domain.user.repository.IUserRepository;
import com.example.utils.dto.request.FilteringDTO;
import com.example.utils.dto.response.BaseResponseDTO;
import com.example.utils.dto.response.ErrorResponseDTO;
import com.example.utils.dto.response.SuccessResponseDTO;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

/** Integration tests for {@link UserControllerImpl}. */
public class UserControllerIT extends BaseIT {
    @Autowired private IUserRepository userRepository;

    @Test
    @DisplayName("Tests the successful creation of a user")
    @Transactional
    void create_Success() throws Exception {
        // Given
        UserCreateRequestDTO userCreateRequestDTO =
                UserCreateRequestDTO.builder()
                        .username(TEST_USERNAME)
                        .password(TEST_PASSWORD)
                        .build();

        // When
        SuccessResponseDTO response =
                performPostAndExpect(
                        USER_BASE_URL,
                        userCreateRequestDTO,
                        CREATED.value(),
                        SuccessResponseDTO.class);

        // Then
        assertNotNull(response);

        UserGetResponseDTO userGetResponseDTO =
                fromJson(toJson(response.getData()), UserGetResponseDTO.class);

        assertNotNull(userGetResponseDTO);

        User user = userRepository.findById(userGetResponseDTO.getId()).orElse(null);

        assertNotNull(user);
        assertUserProperties(
                userGetResponseDTO.getId(),
                TEST_USERNAME,
                TEST_PASSWORD,
                userGetResponseDTO.getCreatedBy(),
                userGetResponseDTO.getUpdatedBy(),
                user);
        assertUserProperties(user, userGetResponseDTO);
    }

    @Test
    @DisplayName("Tests the unsuccessful creation of a user due to the username already existing")
    @Transactional
    void create_UsernameExists() throws Exception {
        // Given
        userRepository.save(
                User.builder()
                        .username(TEST_USERNAME)
                        .password(TEST_PASSWORD)
                        .createdBy(TEST_USERNAME)
                        .build());

        UserCreateRequestDTO userCreateRequestDTO =
                UserCreateRequestDTO.builder()
                        .username(TEST_USERNAME)
                        .password(TEST_PASSWORD)
                        .build();

        // When
        ErrorResponseDTO result =
                performPostAndExpect(
                        USER_BASE_URL,
                        userCreateRequestDTO,
                        CONFLICT.value(),
                        ErrorResponseDTO.class);

        // Then
        assertNotNull(result);
        assertEquals(CONFLICT.value(), result.getErrorCode());
    }

    @Test
    @DisplayName("Tests the unsuccessful creation of a user due to an invalid request")
    @Transactional
    void create_InvalidRequest() throws Exception {
        // Given
        UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO.builder().build();

        // When
        ErrorResponseDTO result =
                performPostAndExpect(
                        USER_BASE_URL,
                        userCreateRequestDTO,
                        BAD_REQUEST.value(),
                        ErrorResponseDTO.class);

        // Then
        assertNotNull(result);
        assertEquals(BAD_REQUEST.value(), result.getErrorCode());
    }

    @Test
    @DisplayName("Tests the successful retrieval of a user")
    @Transactional
    void get_Success() throws Exception {
        // Given
        User user =
                userRepository.save(
                        User.builder()
                                .username(TEST_USERNAME)
                                .password(TEST_PASSWORD)
                                .createdBy(TEST_USERNAME)
                                .build());

        userRepository.save(
                User.builder()
                        .username(TEST_USERNAME2)
                        .password(TEST_PASSWORD2)
                        .createdBy(TEST_USERNAME2)
                        .build());

        Map<String, String> params =
                Map.of(
                        TEST_FIELD_PAGE,
                        String.valueOf(TEST_PAGE2),
                        TEST_FIELD_LIMIT,
                        String.valueOf(TEST_LIMIT),
                        TEST_FIELD_ORDER_BY,
                        TEST_ORDER_BY,
                        TEST_FIELD_ORDER_DIRECTION,
                        TEST_ORDER_DIRECTION);
        String url = USER_BASE_URL + GET_PATH + "?" + toQueryString(params);
        List<FilteringDTO> filteringDTOList =
                List.of(
                        FilteringDTO.builder()
                                .field(FIELD_ID)
                                .operator(EQUALS)
                                .value(user.getId().toString())
                                .build(),
                        FilteringDTO.builder()
                                .field(TEST_FIELD_USERNAME)
                                .operator(EQUALS)
                                .value(user.getUsername())
                                .build(),
                        FilteringDTO.builder()
                                .field(TEST_FIELD_CREATED_AT)
                                .operator(EQUALS)
                                .value(user.getCreatedAt().toString())
                                .build(),
                        FilteringDTO.builder()
                                .field(TEST_FIELD_UPDATED_AT)
                                .operator(EQUALS)
                                .value(user.getUpdatedAt().toString())
                                .build(),
                        FilteringDTO.builder()
                                .field(TEST_FIELD_CREATED_BY)
                                .operator(EQUALS)
                                .value(user.getCreatedBy())
                                .build());

        // When
        SuccessResponseDTO result =
                performPostAndExpect(url, filteringDTOList, OK.value(), SuccessResponseDTO.class);

        // Then
        assertNotNull(result);

        Page<UserGetResponseDTO> userGetResponseDTOPage =
                fromJsonToPage(toJson(result.getData()), UserGetResponseDTO.class);

        assertNotNull(userGetResponseDTOPage);
        assertEquals(1, userGetResponseDTOPage.getTotalElements());

        UserGetResponseDTO userGetResponseDTO = userGetResponseDTOPage.getContent().get(0);

        assertNotNull(userGetResponseDTO);
        assertUserProperties(user, userGetResponseDTO);
    }

    @Test
    @DisplayName(
            "Tests the unsuccessful retrieval of a user due to invalid date format in the filter")
    @Transactional
    void get_InvalidDateFormat() throws Exception {
        // Given
        List<FilteringDTO> filteringDTOList =
                Collections.singletonList(
                        FilteringDTO.builder()
                                .field(TEST_FIELD_CREATED_AT)
                                .operator(EQUALS)
                                .value(TEST_INVALID_DATE)
                                .build());

        // When
        ErrorResponseDTO result =
                performPostAndExpect(
                        USER_BASE_URL + GET_PATH,
                        filteringDTOList,
                        BAD_REQUEST.value(),
                        ErrorResponseDTO.class);

        // Then
        assertNotNull(result);
        assertEquals(BAD_REQUEST.value(), result.getErrorCode());
    }

    @Test
    @DisplayName("Tests the unsuccessful retrieval of a user due to invalid filter")
    @Transactional
    void get_InvalidFilter() throws Exception {
        // Given
        List<FilteringDTO> filteringDTOList =
                Collections.singletonList(FilteringDTO.builder().build());

        // When
        ErrorResponseDTO result =
                performPostAndExpect(
                        USER_BASE_URL + GET_PATH,
                        filteringDTOList,
                        BAD_REQUEST.value(),
                        ErrorResponseDTO.class);

        // Then
        assertNotNull(result);
        assertEquals(BAD_REQUEST.value(), result.getErrorCode());
    }

    @Test
    @DisplayName("Tests the successful update of a user")
    @Transactional
    void update_Success() throws Exception {
        // Given
        User user =
                userRepository.save(
                        User.builder()
                                .username(TEST_USERNAME)
                                .password(TEST_PASSWORD)
                                .createdBy(TEST_USERNAME)
                                .build());
        UserUpdateRequestDTO userUpdateRequestDTO =
                UserUpdateRequestDTO.builder().password(TEST_PASSWORD2).build();

        // When
        SuccessResponseDTO result =
                performPatchAndExpect(
                        USER_BASE_URL + "/" + user.getId(),
                        userUpdateRequestDTO,
                        OK.value(),
                        SuccessResponseDTO.class);

        // Then
        assertNotNull(result);

        UserGetResponseDTO userGetResponseDTO =
                fromJson(toJson(result.getData()), UserGetResponseDTO.class);

        assertNotNull(userGetResponseDTO);

        user = userRepository.findById(userGetResponseDTO.getId()).orElse(null);

        assertNotNull(user);
        assertUserProperties(
                userGetResponseDTO.getId(),
                TEST_USERNAME,
                TEST_PASSWORD2,
                TEST_USERNAME,
                "unknown",
                user);
        assertUserProperties(user, userGetResponseDTO);
    }

    @Test
    @DisplayName("Tests the unsuccessful update of a user due to the user not being found")
    @Transactional
    void update_UserNotFound() throws Exception {
        // Given
        UserUpdateRequestDTO userUpdateRequestDTO =
                UserUpdateRequestDTO.builder().password(TEST_PASSWORD).build();

        // When
        ErrorResponseDTO result =
                performPatchAndExpect(
                        USER_BASE_URL + "/" + TEST_ID,
                        userUpdateRequestDTO,
                        NOT_FOUND.value(),
                        ErrorResponseDTO.class);

        // Then
        assertNotNull(result);
        assertEquals(NOT_FOUND.value(), result.getErrorCode());
    }

    @Test
    @DisplayName("Tests the unsuccessful update of a user due to not modified")
    @Transactional
    void update_NotModified() throws Exception {
        // Given
        User user =
                userRepository.save(
                        User.builder()
                                .username(TEST_USERNAME)
                                .password(TEST_PASSWORD)
                                .createdBy(TEST_USERNAME)
                                .build());
        UserUpdateRequestDTO userUpdateRequestDTO =
                UserUpdateRequestDTO.builder().password(TEST_PASSWORD).build();

        // When
        ErrorResponseDTO result =
                performPatchAndExpect(
                        USER_BASE_URL + "/" + user.getId(),
                        userUpdateRequestDTO,
                        NOT_MODIFIED.value(),
                        ErrorResponseDTO.class);

        // Then
        assertNotNull(result);
        assertEquals(NOT_MODIFIED.value(), result.getErrorCode());
    }

    @Test
    @DisplayName("Tests the unsuccessful update of a user due to an invalid request")
    @Transactional
    void update_InvalidRequest() throws Exception {
        // Given
        UserUpdateRequestDTO userUpdateRequestDTO =
                UserUpdateRequestDTO.builder().password(TEST_INVALID_PASSWORD).build();

        // When
        ErrorResponseDTO result =
                performPatchAndExpect(
                        USER_BASE_URL + "/" + TEST_ID,
                        userUpdateRequestDTO,
                        BAD_REQUEST.value(),
                        ErrorResponseDTO.class);

        // Then
        assertNotNull(result);
        assertEquals(BAD_REQUEST.value(), result.getErrorCode());
    }

    @Test
    @DisplayName("Tests the successful deletion of a user")
    @Transactional
    void delete_Success() throws Exception {
        // Given
        User user =
                userRepository.save(
                        User.builder()
                                .username(TEST_USERNAME)
                                .password(TEST_PASSWORD)
                                .createdBy(TEST_USERNAME)
                                .build());

        // When
        BaseResponseDTO result =
                performDeleteAndExpect(
                        USER_BASE_URL + "/" + user.getId(), OK.value(), BaseResponseDTO.class);

        // Then
        assertNotNull(result);
        assertFalse(userRepository.existsById(user.getId()));
    }

    @Test
    @DisplayName("Tests the unsuccessful deletion of a user due to the user not being found")
    @Transactional
    void delete_UserNotFound() throws Exception {
        // Given

        // When
        ErrorResponseDTO result =
                performDeleteAndExpect(
                        USER_BASE_URL + "/" + TEST_ID, NOT_FOUND.value(), ErrorResponseDTO.class);

        // Then
        assertNotNull(result);
        assertEquals(NOT_FOUND.value(), result.getErrorCode());
    }

    /**
     * Asserts the properties of the given user.
     *
     * @param id The expected ID
     * @param username The expected username
     * @param password The expected password
     * @param createdBy The expected createdBy
     * @param updatedBy The expected updatedBy
     * @param user The user to check
     */
    private void assertUserProperties(
            Long id,
            String username,
            String password,
            String createdBy,
            String updatedBy,
            User user) {
        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(createdBy, user.getCreatedBy());
        assertEquals(updatedBy, user.getUpdatedBy());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    /**
     * Asserts the properties of the given user.
     *
     * @param user The user with the expected properties
     * @param dto The DTO to check
     */
    private void assertUserProperties(User user, UserGetResponseDTO dto) {
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getUsername(), dto.getUsername());
        assertEquals(user.getCreatedBy(), dto.getCreatedBy());
        assertEquals(user.getUpdatedBy(), dto.getUpdatedBy());
        assertEquals(user.getCreatedAt(), dto.getCreatedAt());
        assertEquals(user.getUpdatedAt(), dto.getUpdatedAt());
    }

    /**
     * Converts the given parameters to a query string.
     *
     * @param params The parameters to convert
     * @return The query string
     */
    private String toQueryString(Map<String, String> params) {
        return params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((a, b) -> a + "&" + b)
                .orElse("");
    }
}
