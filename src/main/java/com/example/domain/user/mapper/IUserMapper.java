package com.example.domain.user.mapper;

import com.example.config.MyMapperConfig;
import com.example.domain.user.dto.request.UserCreateRequestDTO;
import com.example.domain.user.dto.response.UserDetailsResponseDTO;
import com.example.domain.user.dto.response.UserResponseDTO;
import com.example.domain.user.model.User;
import com.example.domain.user.model.UserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** Mapper interface for user-related mappings. */
@Mapper(config = MyMapperConfig.class)
public interface IUserMapper {
    /**
     * Converts a User to a UserResponseDTO.
     *
     * @param user the user to convert
     * @param userDetailsResponseDTO the DTO containing the user's details
     * @return the converted DTO
     */
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "createdAt", source = "user.createdAt")
    @Mapping(target = "updatedAt", source = "user.updatedAt")
    @Mapping(target = "createdBy", source = "user.createdBy")
    @Mapping(target = "updatedBy", source = "user.updatedBy")
    @Mapping(target = "details", source = "userDetailsResponseDTO")
    UserResponseDTO toResponseDTO(User user, UserDetailsResponseDTO userDetailsResponseDTO);

    /**
     * Converts a UserCreateRequestDTO to a User.
     *
     * @param userCreateRequestDTO the DTO to convert
     * @param createdBy the user who created the user
     * @param userDetails the entity containing the user's details
     * @return the converted entity
     */
    @Mapping(target = "details", source = "userDetails")
    User toEntity(
            UserCreateRequestDTO userCreateRequestDTO, String createdBy, UserDetails userDetails);
}
