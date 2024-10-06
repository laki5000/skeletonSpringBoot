package com.example.domain.user.controller;

import static com.example.Constants.*;
import static com.example.TestUtils.*;
import static com.example.constants.EndpointConstants.GET_PATH;
import static com.example.constants.EndpointConstants.USER_BASE_URL;
import static com.example.enums.FilterOperator.EQUALS;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import com.example.BaseIT;
import com.example.base.dto.response.BaseResponseDTO;
import com.example.domain.user.dto.request.UserCreateRequestDTO;
import com.example.domain.user.dto.request.UserUpdateRequestDTO;
import com.example.domain.user.dto.response.UserResponseDTO;
import com.example.domain.user.model.User;
import com.example.domain.user.repository.IUserRepository;
import com.example.utils.dto.request.FilteringDTO;
import com.example.utils.dto.response.ErrorResponseDTO;
import com.example.utils.dto.response.SuccessResponseDTO;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/** Integration tests for {@link UserControllerImpl}. */
public class UserControllerIT extends BaseIT {
    @Autowired private IUserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Tests the successful creation of a user")
    @Transactional
    void create_Success() throws Exception {
        // Given
        UserCreateRequestDTO userCreateRequestDTO =
                buildUserCreateRequestDTO(
                        TEST_USERNAME, TEST_PASSWORD, TEST_FIRST_NAME, TEST_LAST_NAME);

        // When
        SuccessResponseDTO response =
                performPostAndExpect(
                        USER_BASE_URL,
                        userCreateRequestDTO,
                        CREATED.value(),
                        SuccessResponseDTO.class);

        // Then
        assertNotNull(response);

        UserResponseDTO userResponseDTO =
                fromJson(toJson(response.getData()), UserResponseDTO.class);

        assertNotNull(userResponseDTO);

        User user = userRepository.findById(userResponseDTO.getId()).orElse(null);

        assertNotNull(user);
        assertUserProperties(
                userResponseDTO.getId(),
                TEST_USERNAME,
                TEST_PASSWORD,
                TEST_FIRST_NAME,
                TEST_LAST_NAME,
                user);
        assertUserProperties(user, userResponseDTO);
    }

    @Test
    @DisplayName("Tests the unsuccessful creation of a user due to the username already existing")
    @Transactional
    void create_UsernameExists() throws Exception {
        // Given
        userRepository.save(
                buildUser(
                        null,
                        TEST_USERNAME,
                        TEST_PASSWORD,
                        null,
                        null,
                        TEST_USERNAME,
                        null,
                        null,
                        TEST_FIRST_NAME,
                        TEST_LAST_NAME,
                        null,
                        null,
                        TEST_USERNAME,
                        null));

        UserCreateRequestDTO userCreateRequestDTO =
                buildUserCreateRequestDTO(
                        TEST_USERNAME, TEST_PASSWORD, TEST_FIRST_NAME, TEST_LAST_NAME);

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
        UserCreateRequestDTO userCreateRequestDTO =
                buildUserCreateRequestDTO(null, null, null, null);

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
                        buildUser(
                                null,
                                TEST_USERNAME,
                                TEST_PASSWORD,
                                null,
                                null,
                                TEST_USERNAME,
                                null,
                                null,
                                TEST_FIRST_NAME,
                                TEST_LAST_NAME,
                                null,
                                null,
                                TEST_USERNAME,
                                null));

        userRepository.save(
                buildUser(
                        null,
                        TEST_USERNAME2,
                        TEST_PASSWORD2,
                        null,
                        null,
                        TEST_USERNAME,
                        null,
                        null,
                        TEST_FIRST_NAME2,
                        TEST_LAST_NAME2,
                        null,
                        null,
                        TEST_USERNAME,
                        null));

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
                        buildFilteringDTO(TEST_FIELD_ID, EQUALS, user.getId().toString(), null),
                        buildFilteringDTO(TEST_FIELD_USERNAME, EQUALS, user.getUsername(), null),
                        buildFilteringDTO(
                                TEST_FIELD_CREATED_AT,
                                EQUALS,
                                user.getCreatedAt().toString(),
                                null),
                        buildFilteringDTO(
                                TEST_FIELD_UPDATED_AT,
                                EQUALS,
                                user.getUpdatedAt().toString(),
                                null),
                        buildFilteringDTO(TEST_FIELD_CREATED_BY, EQUALS, user.getCreatedBy(), null),
                        buildFilteringDTO(
                                TEST_FIELD_DETAILS_ID,
                                EQUALS,
                                user.getDetails().getId().toString(),
                                null),
                        buildFilteringDTO(
                                TEST_FIELD_DETAILS_FIRST_NAME, EQUALS, TEST_FIRST_NAME, null),
                        buildFilteringDTO(
                                TEST_FIELD_DETAILS_LAST_NAME, EQUALS, TEST_LAST_NAME, null),
                        buildFilteringDTO(
                                TEST_FIELD_DETAILS_CREATED_AT,
                                EQUALS,
                                user.getDetails().getCreatedAt().toString(),
                                null),
                        buildFilteringDTO(
                                TEST_FIELD_DETAILS_UPDATED_AT,
                                EQUALS,
                                user.getDetails().getUpdatedAt().toString(),
                                null),
                        buildFilteringDTO(
                                TEST_FIELD_DETAILS_CREATED_BY,
                                EQUALS,
                                user.getDetails().getCreatedBy(),
                                null));

        // When
        SuccessResponseDTO result =
                performPostAndExpect(url, filteringDTOList, OK.value(), SuccessResponseDTO.class);

        // Then
        assertNotNull(result);

        Page<UserResponseDTO> userResponseDTOPage =
                fromJsonToPage(toJson(result.getData()), UserResponseDTO.class);

        assertNotNull(userResponseDTOPage);
        assertEquals(1, userResponseDTOPage.getTotalElements());

        UserResponseDTO userResponseDTO = userResponseDTOPage.getContent().get(0);

        assertNotNull(userResponseDTO);
        assertUserProperties(user, userResponseDTO);
    }

    @Test
    @DisplayName(
            "Tests the unsuccessful retrieval of a user due to invalid date format in the filter")
    @Transactional
    void get_InvalidDateFormat() throws Exception {
        // Given
        List<FilteringDTO> filteringDTOList =
                Collections.singletonList(
                        buildFilteringDTO(TEST_FIELD_CREATED_AT, EQUALS, TEST_INVALID_DATE, null));

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
                        buildUser(
                                null,
                                TEST_USERNAME,
                                TEST_PASSWORD,
                                null,
                                null,
                                TEST_USERNAME,
                                null,
                                null,
                                TEST_FIRST_NAME,
                                TEST_LAST_NAME,
                                null,
                                null,
                                TEST_USERNAME,
                                null));
        UserUpdateRequestDTO userUpdateRequestDTO =
                buildUserUpdateRequestDTO(TEST_PASSWORD2, TEST_FIRST_NAME2, TEST_LAST_NAME2);

        // When
        SuccessResponseDTO result =
                performPatchAndExpect(
                        USER_BASE_URL + "/" + user.getId(),
                        userUpdateRequestDTO,
                        OK.value(),
                        SuccessResponseDTO.class);

        // Then
        assertNotNull(result);

        UserResponseDTO userResponseDTO = fromJson(toJson(result.getData()), UserResponseDTO.class);

        assertNotNull(userResponseDTO);

        user = userRepository.findById(userResponseDTO.getId()).orElse(null);

        assertNotNull(user);
        assertUserProperties(
                userResponseDTO.getId(),
                TEST_USERNAME,
                TEST_PASSWORD2,
                TEST_FIRST_NAME2,
                TEST_LAST_NAME2,
                user);
        assertUserProperties(user, userResponseDTO);
    }

    @Test
    @DisplayName("Tests the unsuccessful update of a user due to the user not being found")
    @Transactional
    void update_UserNotFound() throws Exception {
        // Given
        UserUpdateRequestDTO userUpdateRequestDTO =
                buildUserUpdateRequestDTO(TEST_PASSWORD, TEST_FIRST_NAME, TEST_LAST_NAME);

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
                        buildUser(
                                null,
                                TEST_USERNAME,
                                TEST_PASSWORD,
                                null,
                                null,
                                TEST_USERNAME,
                                null,
                                null,
                                TEST_FIRST_NAME,
                                TEST_LAST_NAME,
                                null,
                                null,
                                TEST_USERNAME,
                                null));
        UserUpdateRequestDTO userUpdateRequestDTO =
                buildUserUpdateRequestDTO(TEST_PASSWORD, TEST_FIRST_NAME, TEST_LAST_NAME);

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
                buildUserUpdateRequestDTO(TEST_INVALID_PASSWORD, null, null);

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
                        buildUser(
                                null,
                                TEST_USERNAME,
                                TEST_PASSWORD,
                                null,
                                null,
                                TEST_USERNAME,
                                null,
                                null,
                                TEST_FIRST_NAME,
                                TEST_LAST_NAME,
                                null,
                                null,
                                TEST_USERNAME,
                                null));

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
     * @param firstName The expected first name
     * @param lastName The expected last name
     * @param user The user to check
     */
    private void assertUserProperties(
            Long id,
            String username,
            String password,
            String firstName,
            String lastName,
            User user) {
        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertTrue(passwordEncoder.matches(password, user.getPassword()));
        assertEquals(firstName, user.getDetails().getFirstName());
        assertEquals(lastName, user.getDetails().getLastName());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    /**
     * Asserts the properties of the given user.
     *
     * @param user The user with the expected properties
     * @param dto The DTO to check
     */
    private void assertUserProperties(User user, UserResponseDTO dto) {
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getUsername(), dto.getUsername());
        assertEquals(user.getCreatedBy(), dto.getCreatedBy());
        assertEquals(user.getUpdatedBy(), dto.getUpdatedBy());
        assertEquals(user.getCreatedAt(), dto.getCreatedAt());
        assertEquals(user.getUpdatedAt(), dto.getUpdatedAt());
        assertEquals(user.getDetails().getFirstName(), dto.getDetails().getFirstName());
        assertEquals(user.getDetails().getLastName(), dto.getDetails().getLastName());
        assertEquals(user.getDetails().getCreatedBy(), dto.getDetails().getCreatedBy());
        assertEquals(user.getDetails().getUpdatedBy(), dto.getDetails().getUpdatedBy());
        assertEquals(user.getDetails().getCreatedAt(), dto.getDetails().getCreatedAt());
        assertEquals(user.getDetails().getUpdatedAt(), dto.getDetails().getUpdatedAt());
    }
}
