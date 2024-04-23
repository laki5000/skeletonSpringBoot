package com.example.user.service;

import com.example.user.dto.request.UserCreateRequest;
import com.example.user.mapper.UserMapper;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import com.example.utils.service.BaseServiceForCRUD;
import com.example.utils.service.MessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/** A service class for managing users. */
@Log4j2
@Service
public class UserService extends BaseServiceForCRUD<User, UserCreateRequest> {
    private final UserRepository userRepository;

    /**
     * Constructor.
     *
     * @param userRepository the user repository
     */
    public UserService(MessageService messageService, UserRepository userRepository, UserMapper userMapper) {
        super(messageService, userMapper);
        this.userRepository = userRepository;
    }

    /**
     * Get the user repository.
     *
     * @return the user repository
     */
    @Override
    protected JpaRepository<User, Long> getRepository() {
        log.info("Getting user repository");

        return userRepository;
    }

    @Override
    public User create(UserCreateRequest entity) {
        String createdBy = entity.username();

        return super.create(entity, createdBy);
    }
}
