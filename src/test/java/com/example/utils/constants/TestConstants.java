package com.example.utils.constants;

import com.example.user.dto.request.UserCreateRequest;
import com.example.user.dto.request.UserUpdateRequest;
import com.example.user.dto.response.UserGetResponse;
import com.example.user.model.User;

import java.util.Locale;

import static com.example.utils.constants.TestUtils.*;

public class TestConstants {
    public static final String validKey = "test.valid.key";
    public static final String invalidKey = "test.invalid.key";
    public static final String expectedMessage = "Test message";
    public static final Locale locale = Locale.ENGLISH;
    public static final Long id = 1L;
    public static final Long invalidId = 2L;
    public static final String username = "username";
    public static final String invalidUsername = "invalidUsername";
    public static final String password = "password";
    public static final String invalidPassword = "invalidPassword";
    public static final String modifiedPassword = "modifiedPassword";
    public static final UserCreateRequest cRequest = getUserCreateRequest(username, password);
    public static final UserUpdateRequest uRequest = getUserUpdateRequest(id, modifiedPassword);
    public static final UserUpdateRequest notModifiedURequest = getUserUpdateRequest(id, password);
    public static final User entity = getUser(id, username, password, null, username, null, null);
    public static final UserGetResponse response = getUserGetResponse(1L, username, null, null, username, null);
}
