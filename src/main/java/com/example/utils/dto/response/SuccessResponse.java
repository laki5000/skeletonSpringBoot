package com.example.utils.dto.response;

import lombok.Getter;

/** Success response class. */
@Getter
public class SuccessResponse extends BaseResponse {
    private Object data;

    public SuccessResponse(String message, Object data) {
        super(message);
        this.data = data;
    }
}
