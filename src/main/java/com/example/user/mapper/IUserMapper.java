package com.example.user.mapper;

import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.response.UserGetResponseDTO;
import com.example.user.model.User;
import org.mapstruct.Mapper;

/** Mapper interface for user-related operations. */
@Mapper(componentModel = "spring")
public interface IUserMapper {
  /**
   * Converts a user to a user get response DTO.
   *
   * @param user the user to convert
   * @return the user get response DTO
   */
  UserGetResponseDTO toGetResponseDTO(User user);

  /**
   * Converts a user create request DTO to a user.
   *
   * @param userCreateRequestDTO the user create request DTO to convert
   * @param updatedBy the user who updated the user
   * @return the user
   */
  User toEntity(UserCreateRequestDTO userCreateRequestDTO, String updatedBy);
}
