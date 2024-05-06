package com.example;

import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.request.UserUpdateRequestDTO;
import org.json.simple.JSONObject;

public class TestUtils {
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
