package com.example;

import com.example.user.dto.request.UserCreateRequest;
import com.example.user.dto.request.UserUpdateRequest;
import com.example.user.dto.response.UserGetResponse;
import com.example.user.model.User;
import org.json.simple.JSONObject;

import java.time.Instant;

public class TestUtils {
    public static UserCreateRequest getUserCreateRequest(String username, String password) {
        return new UserCreateRequest(username, password);
    }

    public static UserUpdateRequest getUserUpdateRequest(Long id, String password) {
        return new UserUpdateRequest(id, password);
    }

    public static User getUser(Long id, String username, String password, Instant createdAt, String createdBy, Instant updatedAt, String updatedBy) {
        return new User(id, username, password, createdAt, createdBy, updatedAt, updatedBy);
    }

    public static UserGetResponse getUserGetResponse(Long id, String username, Instant createdAt, Instant updatedAt, String createdBy, String updatedBy) {
        return new UserGetResponse(id, username, createdAt, updatedAt, createdBy, updatedBy);
    }

    public static String convertDtoToJson(Object dto) {
        JSONObject jsonObject = new JSONObject();

        switch (dto.getClass().getSimpleName()) {
            case "UserCreateRequest":
                UserCreateRequest userCreateRequest = (UserCreateRequest) dto;

                jsonObject.put("username", userCreateRequest.getUsername());
                jsonObject.put("password", userCreateRequest.getPassword());

                break;
            case "UserUpdateRequest":
                UserUpdateRequest userUpdateRequest = (UserUpdateRequest) dto;

                jsonObject.put("id", userUpdateRequest.getId());
                jsonObject.put("password", userUpdateRequest.getPassword());

                break;
            default:
                break;
        }

        return jsonObject.toJSONString();
    }
}
