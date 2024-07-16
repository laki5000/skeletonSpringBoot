package com.example.user.controller;

import com.example.user.service.IUserService;
import com.example.utils.service.IMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller class for managing user-related endpoints. */
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final IMessageService messageService;
    private final IUserService userService;
}
