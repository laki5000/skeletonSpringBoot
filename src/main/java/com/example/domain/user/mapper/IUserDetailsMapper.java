package com.example.domain.user.mapper;

import com.example.config.MyMapperConfig;
import com.example.domain.user.dto.request.UserDetailsRequestDTO;
import com.example.domain.user.dto.response.UserDetailsResponseDTO;
import com.example.domain.user.model.UserDetails;
import org.mapstruct.Mapper;

/** Mapper interface for user details-related mappings. */
@Mapper(config = MyMapperConfig.class)
public interface IUserDetailsMapper {
    /**
     * Converts a UserDetails to a UserDetailsResponseDTO.
     *
     * @param userDetails the entity to convert
     * @return the converted DTO
     */
    UserDetailsResponseDTO toResponseDTO(UserDetails userDetails);

    /**
     * Converts a UserCreateRequestDTO to a User.
     *
     * @param userDetailsRequestDTO the DTO to convert
     * @param createdBy the user who created the user
     * @return the converted entity
     */
    UserDetails toEntity(UserDetailsRequestDTO userDetailsRequestDTO, String createdBy);
}
