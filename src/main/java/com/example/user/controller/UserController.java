package com.example.user.controller;

import com.example.user.dto.request.UserCreateRequest;
import com.example.user.dto.request.UserUpdateRequest;
import com.example.user.dto.response.UserGetResponse;
import com.example.user.model.User;
import com.example.user.service.UserService;
import com.example.utils.controller.BaseControllerForCRUD;
import com.example.utils.service.BaseServiceForCRUD;
import com.example.utils.service.MessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for managing users. */
@Log4j2
@RestController
@RequestMapping("${api.base.path}/users")
public class UserController extends BaseControllerForCRUD<User, UserCreateRequest, UserUpdateRequest, UserGetResponse> {
    private final UserService userService;

    /**
     * Override the getService method to return the user service.
     *
     * @return the user service
     */
    @Override
    protected BaseServiceForCRUD<User, UserCreateRequest, UserUpdateRequest, UserGetResponse> getService() {
        log.info("Getting user service");

        return userService;
    }

    /**
     * Constructor.
     *
     * @param userService the user service
     */
    public UserController(MessageService messageService, UserService userService) {
        super(messageService, User.class);
        this.userService = userService;
    }
}
