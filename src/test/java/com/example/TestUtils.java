package com.example;

import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.request.UserUpdateRequestDTO;
import com.example.user.dto.response.UserGetResponseDTO;
import com.example.user.model.User;
import org.json.simple.JSONObject;

import java.time.Instant;

public class TestUtils {
    public static UserCreateRequestDTO getUserCreateRequest(String username, String password) {
        return new UserCreateRequestDTO(username, password);
    }

    public static UserUpdateRequestDTO getUserUpdateRequest(Long id, String password) {
        return new UserUpdateRequestDTO(id, password);
    }

    public static User getUser(Long id, String username, String password, Instant createdAt, String createdBy, Instant updatedAt, String updatedBy) {
        return new User(id, username, password, createdAt, createdBy, updatedAt, updatedBy);
    }

    public static UserGetResponseDTO getUserGetResponse(Long id, String username, Instant createdAt, Instant updatedAt, String createdBy, String updatedBy) {
        return new UserGetResponseDTO(id, username, createdAt, updatedAt, createdBy, updatedBy);
    }

    public static String convertDtoToJson(Object dto) {
        JSONObject jsonObject = new JSONObject();

        switch (dto.getClass().getSimpleName()) {
            case "UserCreateRequest":
                UserCreateRequestDTO userCreateRequest = (UserCreateRequestDTO) dto;

                jsonObject.put("username", userCreateRequest.getUsername());
                jsonObject.put("password", userCreateRequest.getPassword());

                break;
            case "UserUpdateRequest":
                UserUpdateRequestDTO userUpdateRequest = (UserUpdateRequestDTO) dto;

                jsonObject.put("id", userUpdateRequest.getId());
                jsonObject.put("password", userUpdateRequest.getPassword());

                break;
            default:
                break;
        }

        return jsonObject.toJSONString();
    }
}
