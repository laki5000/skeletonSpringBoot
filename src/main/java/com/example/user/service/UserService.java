package com.example.user.service;

import com.example.user.dto.request.UserCreateRequest;
import com.example.user.dto.request.UserUpdateRequest;
import com.example.user.dto.response.UserGetResponse;
import com.example.user.mapper.UserMapper;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import com.example.utils.service.BaseServiceForCRUD;
import com.example.utils.service.MessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** A service class for managing users. */
@Log4j2
@Service
public class UserService extends BaseServiceForCRUD<User, UserCreateRequest, UserUpdateRequest, UserGetResponse> {
    private final UserRepository userRepository;

    /**
     * Constructor.
     *
     * @param userRepository the user repository
     */
    public UserService(MessageService messageService, UserRepository userRepository, UserMapper userMapper) {
        super(messageService, userMapper, User.class);
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

    /**
     * Validate the user creation request.
     *
     * @param entity the user creation request
     * @return an error message if the request is invalid, null otherwise
     */
    @Override
    protected String validateCreate(UserCreateRequest entity) {
        return userRepository.existsByUsername(entity.username()) ? "conflict.username.exists" : null;
    }

    /**
     * Validate the user update request.
     *
     * @param update the user update request
     * @return an error message if the request is invalid, null otherwise
     */
    @Override
    protected Object validateUpdate(UserUpdateRequest update) {
        Optional<User> userOptional = userRepository.findById(update.id());

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            return "notfound.user";
        }
    }

    /**
     * Get the id from the user update request.
     *
     * @param update the user update request
     * @return the id
     */
    @Override
    protected Long getIdFromUpdateRequest(UserUpdateRequest update) {
        return update.id();
    }
}
