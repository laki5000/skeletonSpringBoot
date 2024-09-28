package com.example.domain.user.service;

import com.example.base.service.IBaseService;
import com.example.domain.user.dto.request.UserCreateRequestDTO;
import com.example.domain.user.dto.request.UserUpdateRequestDTO;
import com.example.domain.user.dto.response.UserResponseDTO;

/** Service interface for user service. */
public interface IUserService
        extends IBaseService<UserCreateRequestDTO, UserUpdateRequestDTO, UserResponseDTO> {}
