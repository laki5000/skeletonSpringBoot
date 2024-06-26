package com.example;

import com.example.user.dto.request.UserCreateRequest;
import com.example.user.dto.request.UserUpdateRequest;
import com.example.user.dto.response.UserGetResponse;
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
    public static final UserCreateRequest cRequest = getUserCreateRequest(username, password);
    public static final UserCreateRequest otherCRequest = getUserCreateRequest(otherUsername, password);
    public static final UserCreateRequest invalidCRequest = getUserCreateRequest(invalidUsername, invalidPassword);
    public static final UserUpdateRequest uRequest = getUserUpdateRequest(id, modifiedPassword);
    public static final UserUpdateRequest otherURequest = getUserUpdateRequest(invalidId, modifiedPassword);
    public static final UserUpdateRequest invalidURequest = getUserUpdateRequest(id, invalidPassword);
    public static final UserUpdateRequest notModifiedURequest = getUserUpdateRequest(id, password);
    public static final User entity = getUser(id, username, password, null, username, null, null);
    public static final UserGetResponse response = getUserGetResponse(1L, username, null, null, username, null);
}
