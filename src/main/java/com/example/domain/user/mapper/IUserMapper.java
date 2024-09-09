package com.example.domain.user.mapper;

import com.example.config.IMapperBaseConfig;
import com.example.domain.user.dto.request.UserCreateRequestDTO;
import com.example.domain.user.dto.response.UserGetResponseDTO;
import com.example.domain.user.model.User;
import org.mapstruct.Mapper;

/** Mapper interface for user-related operations. */
@Mapper(config = IMapperBaseConfig.class)
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
     * @param createdBy the user who created the user
     * @return the user
     */
    User toEntity(UserCreateRequestDTO userCreateRequestDTO, String createdBy);
}
