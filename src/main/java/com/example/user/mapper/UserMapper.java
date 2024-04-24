package com.example.user.mapper;

import com.example.user.dto.request.UserCreateRequest;
import com.example.user.dto.request.UserUpdateRequest;
import com.example.user.dto.response.UserGetResponse;
import com.example.user.model.User;
import com.example.utils.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** Mapper for user entities. */
@Mapper
public interface UserMapper extends BaseMapper<User, UserCreateRequest, UserUpdateRequest, UserGetResponse> {
    /**
     * Maps a user create request to a user entity.
     *
     * @param dto the user create request
     * @return the user entity
     */
    @Mapping(target = "username", source = "dto.username")
    @Mapping(target = "password", source = "dto.password")
    @Mapping(target = "createdBy", source = "dto.username")
    User toEntity(UserCreateRequest dto);

    /**
     * Maps an entity to a user get response.
     *
     * @param entity the user entity
     * @return the user entity
     */
    UserGetResponse toGetResponse(User entity);
}
