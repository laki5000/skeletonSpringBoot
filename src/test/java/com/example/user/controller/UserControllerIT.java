package com.example.user.controller;

import static com.example.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import com.example.BaseIT;
import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.request.UserUpdateRequestDTO;
import com.example.user.dto.response.UserGetResponseDTO;
import com.example.user.model.User;
import com.example.user.repository.IUserRepository;
import com.example.utils.dto.response.BaseResponseDTO;
import com.example.utils.dto.response.ErrorResponseDTO;
import com.example.utils.dto.response.SuccessResponseDTO;
import java.time.Instant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

/** Integration tests for {@link UserController}. */
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
                        USER_API_URL,
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
        assertEquals(TEST_USERNAME, user.getUsername());
        assertEquals(TEST_PASSWORD, user.getPassword());
        assertEquals("unknown", user.getCreatedBy());
        assertNull(user.getUpdatedBy());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
        assertEquals(TEST_USERNAME, userGetResponseDTO.getUsername());
        assertEquals("unknown", userGetResponseDTO.getCreatedBy());
        assertNull(userGetResponseDTO.getUpdatedBy());
        assertNotNull(userGetResponseDTO.getCreatedAt());
        assertNotNull(userGetResponseDTO.getUpdatedAt());
        assertEquals(user.getCreatedAt(), userGetResponseDTO.getCreatedAt());
        assertEquals(user.getUpdatedAt(), userGetResponseDTO.getUpdatedAt());
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
                        USER_API_URL,
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
                        USER_API_URL,
                        userCreateRequestDTO,
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
                performPutAndExpect(
                        USER_API_URL + "/" + user.getId(),
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
        assertEquals(TEST_USERNAME, user.getUsername());
        assertEquals(TEST_PASSWORD2, user.getPassword());
        assertEquals(TEST_USERNAME, user.getCreatedBy());
        assertEquals("unknown", user.getUpdatedBy());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
        assertEquals(TEST_USERNAME, userGetResponseDTO.getUsername());
        assertEquals(TEST_USERNAME, userGetResponseDTO.getCreatedBy());
        assertEquals("unknown", userGetResponseDTO.getUpdatedBy());
        assertNotNull(userGetResponseDTO.getCreatedAt());
        assertNotNull(userGetResponseDTO.getUpdatedAt());
        assertEquals(user.getCreatedAt(), userGetResponseDTO.getCreatedAt());
        assertEquals(user.getUpdatedAt(), userGetResponseDTO.getUpdatedAt());
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
                performPutAndExpect(
                        USER_API_URL + "/" + TEST_ID,
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
                performPutAndExpect(
                        USER_API_URL + "/" + user.getId(),
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
        UserUpdateRequestDTO userUpdateRequestDTO = UserUpdateRequestDTO.builder().build();

        // When
        ErrorResponseDTO result =
                performPutAndExpect(
                        USER_API_URL + "/" + TEST_ID,
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
                        USER_API_URL + "/" + user.getId(), OK.value(), BaseResponseDTO.class);

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
                        USER_API_URL + "/" + TEST_ID, NOT_FOUND.value(), ErrorResponseDTO.class);

        // Then
        assertNotNull(result);
        assertEquals(NOT_FOUND.value(), result.getErrorCode());
    }

    @Test
    @DisplayName("Tests the successful retrieval of users")
    @Transactional
    void get_Success() throws Exception {
        // Given
        userRepository.save(
                User.builder()
                        .username(TEST_USERNAME)
                        .password(TEST_PASSWORD)
                        .createdBy(TEST_USERNAME)
                        .build());

        User user =
                userRepository.save(
                        User.builder()
                                .username(TEST_USERNAME2)
                                .password(TEST_PASSWORD2)
                                .createdBy(TEST_USERNAME2)
                                .build());
        String url =
                getUrlWithParams(
                        user.getId(),
                        TEST_USERNAME2,
                        user.getCreatedAt(),
                        user.getUpdatedAt(),
                        TEST_USERNAME,
                        "",
                        0,
                        10,
                        "id",
                        "asc");

        // When
        SuccessResponseDTO result = performGetAndExpect(url, OK.value(), SuccessResponseDTO.class);

        // Then
        assertNotNull(result);

        Page<UserGetResponseDTO> userGetResponseDTOPage =
                fromJsonToPage(toJson(result.getData()), UserGetResponseDTO.class);

        assertNotNull(userGetResponseDTOPage);
        assertEquals(1, userGetResponseDTOPage.getTotalElements());

        UserGetResponseDTO userGetResponseDTO = userGetResponseDTOPage.getContent().get(0);

        assertEquals(user.getId(), userGetResponseDTO.getId());
        assertEquals(TEST_USERNAME2, userGetResponseDTO.getUsername());
        assertEquals(user.getCreatedAt(), userGetResponseDTO.getCreatedAt());
        assertEquals(user.getUpdatedAt(), userGetResponseDTO.getUpdatedAt());
        assertEquals(TEST_USERNAME2, userGetResponseDTO.getCreatedBy());
        assertNull(userGetResponseDTO.getUpdatedBy());
    }

    /**
     * Returns the URL with the specified parameters.
     *
     * @param id the ID
     * @param username the username
     * @param createdAt the created at
     * @param updatedAt the updated at
     * @param createdBy the created by
     * @param updatedBy the updated by
     * @param page the page
     * @param limit the limit
     * @param orderBy the order by
     * @param orderDirection the order direction
     * @return the URL with the specified parameters
     */
    private String getUrlWithParams(
            long id,
            String username,
            Instant createdAt,
            Instant updatedAt,
            String createdBy,
            String updatedBy,
            int page,
            int limit,
            String orderBy,
            String orderDirection) {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromUriString(USER_API_URL)
                        .queryParam("id", id)
                        .queryParam("username", username)
                        .queryParam("createdAt", createdAt)
                        .queryParam("updatedAt", updatedAt)
                        .queryParam("createdBy", createdBy)
                        .queryParam("updatedBy", updatedBy)
                        .queryParam("page", page)
                        .queryParam("limit", limit)
                        .queryParam("orderBy", orderBy)
                        .queryParam("orderDirection", orderDirection);

        return builder.toUriString();
    }
}
