package com.example.user.mapper;

import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.request.UserUpdateRequestDTO;
import com.example.user.dto.response.UserGetResponseDTO;
import com.example.user.model.User;
import com.example.utils.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** Mapper for user entities. */
@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserCreateRequestDTO, UserUpdateRequestDTO, UserGetResponseDTO> {
    /**
     * Maps a user create request to a user entity.
     *
     * @param userCreateRequestDTO the user create request
     * @return the user entity
     */
    @Mapping(target = "createdBy", source = "username")
    User toEntity(UserCreateRequestDTO userCreateRequestDTO);

    /**
     * Maps an entity to a user get response.
     *
     * @param user the user entity
     * @return the user entity
     */
    UserGetResponseDTO toGetResponse(User user);
}
