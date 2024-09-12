package com.example.domain.user.controller;

import com.example.domain.user.dto.request.UserCreateRequestDTO;
import com.example.domain.user.dto.request.UserUpdateRequestDTO;
import com.example.base.controller.IBaseController;

/** Controller interface for user controller. */
public interface IUserController
        extends IBaseController<UserCreateRequestDTO, UserUpdateRequestDTO> {}
