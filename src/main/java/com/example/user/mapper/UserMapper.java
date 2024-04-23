package com.example.user.mapper;

import com.example.user.dto.request.UserCreateRequest;
import com.example.user.model.User;
import com.example.utils.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper extends BaseMapper<User, UserCreateRequest> {
    @Mapping(target = "username", source = "dto.username")
    @Mapping(target = "password", source = "dto.password")
    @Mapping(target = "createdBy", source = "dto.username")
    User toEntity(UserCreateRequest dto);
}
