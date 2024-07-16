package com.example.utils.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/** DTO class for the success response. */
@Getter
@SuperBuilder
public class SuccessResponseDTO extends BaseResponseDTO {
  private Object data;
}
