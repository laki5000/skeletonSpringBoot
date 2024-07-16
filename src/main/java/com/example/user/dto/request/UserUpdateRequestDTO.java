package com.example.user.dto.request;

import com.example.user.dto.OnlyPasswordDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/** DTO class for updating a user. */
@Getter
public class UserUpdateRequestDTO extends OnlyPasswordDTO {
  @NotNull(message = "Id is required")
  private Long id;
}
