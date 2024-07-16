package com.example.user.dto.request;

import com.example.user.dto.OnlyPasswordDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/** Dto class for creating a user. */
@Getter
public class UserCreateRequestDTO extends OnlyPasswordDTO {
  @NotNull(message = "Username is required")
  @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
  private String username;
}
