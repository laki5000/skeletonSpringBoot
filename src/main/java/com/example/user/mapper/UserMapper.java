package com.example.user.mapper;

import com.example.user.dto.request.UserCreateRequest;
import com.example.user.dto.request.UserUpdateRequest;
import com.example.user.dto.response.UserGetResponse;
import com.example.user.model.User;
import com.example.utils.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** Mapper for user entities. */
@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserCreateRequest, UserUpdateRequest, UserGetResponse> {
    /**
     * Maps a user create request to a user entity.
     *
     * @param dto the user create request
     * @return the user entity
     */
    @Mapping(target = "createdBy", source = "username")
    User toEntity(UserCreateRequest dto);

    /**
     * Maps an entity to a user get response.
     *
     * @param entity the user entity
     * @return the user entity
     */
    UserGetResponse toGetResponse(User entity);
}
