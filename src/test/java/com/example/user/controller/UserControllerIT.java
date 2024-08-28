package com.example.user.controller;

import static com.example.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

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
        MvcResult result = performPostAndExpect(USER_API_URL, userCreateRequestDTO, 201);

        // Then
        SuccessResponseDTO successResponseDTO =
                fromJson(result.getResponse().getContentAsString(), SuccessResponseDTO.class);

        assertNotNull(successResponseDTO);

        UserGetResponseDTO userGetResponseDTO =
                fromJson(toJson(successResponseDTO.getData()), UserGetResponseDTO.class);

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
        MvcResult result = performPostAndExpect(USER_API_URL, userCreateRequestDTO, 409);

        // Then
        ErrorResponseDTO errorResponseDTO =
                fromJson(result.getResponse().getContentAsString(), ErrorResponseDTO.class);

        assertNotNull(errorResponseDTO);
        assertEquals(409, errorResponseDTO.getErrorCode());
    }

    @Test
    @DisplayName("Tests the unsuccessful creation of a user due to an invalid request")
    @Transactional
    void create_InvalidRequest() throws Exception {
        // Given
        UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO.builder().build();

        // When
        MvcResult result = performPostAndExpect(USER_API_URL, userCreateRequestDTO, 400);

        // Then
        ErrorResponseDTO errorResponseDTO =
                fromJson(result.getResponse().getContentAsString(), ErrorResponseDTO.class);

        assertNotNull(errorResponseDTO);
        assertEquals(400, errorResponseDTO.getErrorCode());
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
                UserUpdateRequestDTO.builder().id(user.getId()).password(TEST_PASSWORD2).build();

        // When
        MvcResult result = performPutAndExpect(USER_API_URL, userUpdateRequestDTO, 200);

        // Then
        SuccessResponseDTO successResponseDTO =
                fromJson(result.getResponse().getContentAsString(), SuccessResponseDTO.class);

        assertNotNull(successResponseDTO);

        UserGetResponseDTO userGetResponseDTO =
                fromJson(toJson(successResponseDTO.getData()), UserGetResponseDTO.class);

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
                UserUpdateRequestDTO.builder().id(TEST_ID).password(TEST_PASSWORD).build();

        // When
        MvcResult result = performPutAndExpect(USER_API_URL, userUpdateRequestDTO, 404);

        // Then
        ErrorResponseDTO errorResponseDTO =
                fromJson(result.getResponse().getContentAsString(), ErrorResponseDTO.class);

        assertNotNull(errorResponseDTO);
        assertEquals(404, errorResponseDTO.getErrorCode());
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
                UserUpdateRequestDTO.builder().id(user.getId()).password(TEST_PASSWORD).build();

        // When
        MvcResult result = performPutAndExpect(USER_API_URL, userUpdateRequestDTO, 304);

        // Then
        ErrorResponseDTO errorResponseDTO =
                fromJson(result.getResponse().getContentAsString(), ErrorResponseDTO.class);

        assertNotNull(errorResponseDTO);
        assertEquals(304, errorResponseDTO.getErrorCode());
    }

    @Test
    @DisplayName("Tests the unsuccessful update of a user due to an invalid request")
    @Transactional
    void update_InvalidRequest() throws Exception {
        // Given
        UserUpdateRequestDTO userUpdateRequestDTO = UserUpdateRequestDTO.builder().build();

        // When
        MvcResult result = performPutAndExpect(USER_API_URL, userUpdateRequestDTO, 400);

        // Then
        ErrorResponseDTO errorResponseDTO =
                fromJson(result.getResponse().getContentAsString(), ErrorResponseDTO.class);

        assertNotNull(errorResponseDTO);
        assertEquals(400, errorResponseDTO.getErrorCode());
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
        MvcResult result = performDeleteAndExpect(USER_API_URL + "?id=" + user.getId(), 200);

        // Then
        BaseResponseDTO baseResponseDTO =
                fromJson(result.getResponse().getContentAsString(), BaseResponseDTO.class);

        assertNotNull(baseResponseDTO);
        assertFalse(userRepository.existsById(user.getId()));
    }

    @Test
    @DisplayName("Tests the unsuccessful deletion of a user due to the user not being found")
    @Transactional
    void delete_UserNotFound() throws Exception {
        // Given

        // When
        MvcResult result = performDeleteAndExpect(USER_API_URL + "?id=" + TEST_ID, 404);

        // Then
        ErrorResponseDTO errorResponseDTO =
                fromJson(result.getResponse().getContentAsString(), ErrorResponseDTO.class);

        assertNotNull(errorResponseDTO);
        assertEquals(404, errorResponseDTO.getErrorCode());
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
        MvcResult result = performGetAndExpect(url, 200);

        // Then
        SuccessResponseDTO successResponseDTO =
                fromJson(result.getResponse().getContentAsString(), SuccessResponseDTO.class);

        assertNotNull(successResponseDTO);

        Page<UserGetResponseDTO> userGetResponseDTOPage =
                fromJsonToPage(toJson(successResponseDTO.getData()), UserGetResponseDTO.class);

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
        return USER_API_URL
                + "?id="
                + id
                + "&username="
                + username
                + "&createdAt="
                + createdAt.toString()
                + "&updatedAt="
                + updatedAt
                + "&createdBy="
                + createdBy
                + "&updatedBy="
                + updatedBy
                + "&page="
                + page
                + "&limit="
                + limit
                + "&orderBy="
                + orderBy
                + "&orderDirection="
                + orderDirection;
    }
}
