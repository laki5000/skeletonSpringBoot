package com.example.user.service;

import com.example.exception.MyConflictException;
import com.example.exception.MyNotFoundException;
import com.example.exception.MyNotModifiedException;
import com.example.user.dto.request.UserCreateRequest;
import com.example.user.dto.request.UserUpdateRequest;
import com.example.user.dto.response.UserGetResponse;
import com.example.user.mapper.UserMapper;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import com.example.utils.repository.BaseRepository;
import com.example.utils.service.BaseServiceForCRUD;
import com.example.utils.service.MessageService;
import lombok.extern.log4j.Log4j2;
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
    protected BaseRepository<User, Long> getRepository() {
        log.info("Getting user repository");

        return userRepository;
    }

    /**
     * Validate the user create request.
     *
     * @param entity the user create request
     */
    @Override
    protected void validateCreate(UserCreateRequest entity) {
        userRepository.existsByUsername(entity.username());

        if (userRepository.existsByUsername(entity.username())) {
            throw new MyConflictException(getMessageService().getMessage("conflict.username.exists"));
        }
    }

    /**
     * Validate the user update request.
     *
     * @param update the entity to validate
     * @return the updated user
     */
    @Override
    protected User validateUpdate(UserUpdateRequest update) {
        return validateById(update.id());
    }

    /**
     * Validate the user delete request.
     *
     * @param id the user id
     */
    @Override
    protected void validateDelete(Long id) {
        validateById(id);
    }

    /**
     * Validate the user by id.
     *
     * @param id the user id
     * @return the user if the id is valid, an error message otherwise
     */
    private User validateById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new MyNotFoundException(getMessageService().getMessage("not.found.user"));
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

    /**
     * Update the user.
     *
     * @param entity the user to update
     * @param update the user update request
     */
    @Override
    protected void doUpdate(User entity, UserUpdateRequest update) {
        boolean updated = false;

        if (!update.password().equals(entity.getPassword())) {
            entity.setPassword(update.password());
            updated = true;
        }

        if (!updated) {
            throw new MyNotModifiedException(getMessageService().getMessage("not.modified"));
        }
    }
}
