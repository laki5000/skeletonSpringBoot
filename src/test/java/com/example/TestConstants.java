package com.example;

import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.request.UserUpdateRequestDTO;
import com.example.user.dto.response.UserGetResponseDTO;
import com.example.user.model.User;

import java.util.Locale;

import static com.example.TestUtils.*;

public class TestConstants {
    public static final String validKey = "test.valid.key";
    public static final String invalidKey = "test.invalid.key";
    public static final String expectedMessage = "Test message";
    public static final Locale locale = Locale.ENGLISH;
    public static final Long id = 1L;
    public static final Long invalidId = 2L;
    public static final String username = "username";
    public static final String otherUsername = "otherUsername";
    public static final String invalidUsername = "inv";
    public static final String password = "Val1dP@ssword";
    public static final String modifiedPassword = "M0d1f13dP@ssword";
    public static final String invalidPassword = "invalidPassword";
    public static final UserCreateRequestDTO cRequest = getUserCreateRequest(username, password);
    public static final UserCreateRequestDTO otherCRequest = getUserCreateRequest(otherUsername, password);
    public static final UserCreateRequestDTO invalidCRequest = getUserCreateRequest(invalidUsername, invalidPassword);
    public static final UserUpdateRequestDTO uRequest = getUserUpdateRequest(id, modifiedPassword);
    public static final UserUpdateRequestDTO otherURequest = getUserUpdateRequest(invalidId, modifiedPassword);
    public static final UserUpdateRequestDTO invalidURequest = getUserUpdateRequest(id, invalidPassword);
    public static final UserUpdateRequestDTO notModifiedURequest = getUserUpdateRequest(id, password);
    public static final User entity = getUser(id, username, password, null, username, null, null);
    public static final UserGetResponseDTO response = getUserGetResponse(1L, username, null, null, username, null);
}
