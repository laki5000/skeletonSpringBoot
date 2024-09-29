package com.example.domain.user.mapper;

import com.example.config.MyMapperConfig;
import com.example.domain.user.dto.request.UserCreateRequestDTO;
import com.example.domain.user.dto.response.UserResponseDTO;
import com.example.domain.user.model.User;
import org.mapstruct.*;

/** Mapper interface for user-related mappings. */
@Mapper(config = MyMapperConfig.class)
public interface IUserMapper {
    /**
     * Converts a User to a UserResponseDTO.
     *
     * @param user the user to convert
     * @return the converted DTO
     */
    UserResponseDTO toResponseDTO(User user);

    /**
     * Converts a UserCreateRequestDTO to a User.
     *
     * @param userCreateRequestDTO the DTO to convert
     * @param createdBy the user who created the user
     * @return the converted entity
     */
    @Mapping(target = "createdBy", expression = "java(createdBy)")
    @Mapping(target = "details.createdBy", expression = "java(createdBy)")
    User toEntity(UserCreateRequestDTO userCreateRequestDTO, @Context String createdBy);
}
