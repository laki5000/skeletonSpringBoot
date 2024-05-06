package com.example.user.controller;

import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.request.UserUpdateRequestDTO;
import com.example.user.dto.response.UserGetResponseDTO;
import com.example.user.model.User;
import com.example.user.service.UserService;
import com.example.utils.controller.BaseController;
import com.example.utils.service.BaseService;
import com.example.utils.service.MessageService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for managing users. */
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2"})
@Log4j2
@RestController
@RequestMapping("api/v1/users")
public class UserController extends BaseController<User, UserCreateRequestDTO, UserUpdateRequestDTO, UserGetResponseDTO> {
    private final UserService userService;

    /**
     * Override the getService method to return the user service.
     *
     * @return the user service
     */
    @Override
    protected BaseService<User, UserCreateRequestDTO, UserUpdateRequestDTO, UserGetResponseDTO> getService() {
        log.info("Getting user service");

        return userService;
    }

    /**
     * Constructor.
     *
     * @param messageService the message service
     * @param userService    the user service
     */
    public UserController(MessageService messageService, UserService userService) {
        super(messageService);
        this.userService = userService;
    }
}
