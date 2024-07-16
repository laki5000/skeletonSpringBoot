package com.example.user.service;

import com.example.user.mapper.IUserMapper;
import com.example.user.model.User;
import com.example.user.repository.IUserRepository;
import com.example.utils.service.IMessageService;
import com.example.utils.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/** A service class for managing user-related operations. */
@Log4j2
@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final IMessageService messageService;
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;
}
