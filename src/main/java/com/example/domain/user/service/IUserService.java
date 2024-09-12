package com.example.domain.user.service;

import com.example.domain.user.dto.request.UserCreateRequestDTO;
import com.example.domain.user.dto.request.UserUpdateRequestDTO;
import com.example.domain.user.dto.response.UserResponseDTO;
import com.example.base.service.IBaseService;

/** Service interface for user service. */
public interface IUserService
        extends IBaseService<UserCreateRequestDTO, UserUpdateRequestDTO, UserResponseDTO> {}
