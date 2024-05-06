package com.example.utils.dto.response;

import lombok.Getter;

/** Success response class. */
@Getter
public class SuccessResponseDTO extends BaseResponseDTO {
    private Object data;

    public SuccessResponseDTO(String message, Object data) {
        super(message);
        this.data = data;
    }
}
