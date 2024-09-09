package com.example.domain.user.mapper;

import com.example.config.IMapperBaseConfig;
import com.example.domain.user.dto.request.UserCreateRequestDTO;
import com.example.domain.user.dto.response.UserGetResponseDTO;
import com.example.domain.user.model.User;
import org.mapstruct.Mapper;

/** Mapper interface for user-related mappings. */
@Mapper(config = IMapperBaseConfig.class)
public interface IUserMapper {
    /**
     * Converts a User to a UserGetResponseDTO.
     *
     * @param user the user to convert
     * @return the converted DTO
     */
    UserGetResponseDTO toGetResponseDTO(User user);

    /**
     * Converts a UserCreateRequestDTO to a User.
     *
     * @param userCreateRequestDTO the DTO to convert
     * @param createdBy the user who created the user
     * @return the converted entity
     */
    User toEntity(UserCreateRequestDTO userCreateRequestDTO, String createdBy);
}
