package com.example.user.service;

import com.example.exception.NotFoundException;
import com.example.exception.NotModifiedException;
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
        log.info("Validating user create request");

        validateByUsername(entity.getUsername());

        log.info("User create request is valid");
    }

    /**
     * Validate the user update request.
     *
     * @param update the entity to validate
     * @return the updated user
     */
    @Override
    protected User validateUpdate(UserUpdateRequest update) {
        log.info("Validating user update request");

        User user = validateById(update.getId());

        log.info("User update request is valid");

        return user;
    }

    /**
     * Validate the user delete request.
     *
     * @param id the user id
     */
    @Override
    protected void validateDelete(Long id) {
        log.info("Validating user delete request");

        validateById(id);

        log.info("User delete request is valid");
    }

    /**
     * Validate the user by id.
     *
     * @param id the user id
     * @return the user if the id is valid, an error message otherwise
     */
    private User validateById(Long id) {
        log.info("Validating user by id: {}", id);

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        log.info("User found with id: {}", id);

        return user;
    }

    /**
     * Validate the user by username.
     *
     * @param username the username
     */
    private void validateByUsername(String username) {
        log.info("Validating user by username: {}", username);

        userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found with username: " + username));

        log.info("User found with username: {}", username);
    }

    /**
     * Get the id from the user update request.
     *
     * @param update the user update request
     * @return the id
     */
    @Override
    protected Long getIdFromUpdateRequest(UserUpdateRequest update) {
        log.info("Getting id from user update request");

        Long id = update.getId();

        log.info("Got id from user update request: {}", id);

        return id;
    }

    /**
     * Update the user.
     *
     * @param entity the user to update
     * @param update the user update request
     */
    @Override
    protected void doUpdate(User entity, UserUpdateRequest update) {
        log.info("Updating user");

        boolean updated = false;

        if (!update.getPassword().equals(entity.getPassword())) {
            log.info("Updating user password");

            entity.setPassword(update.getPassword());
            updated = true;
        }

        if (!updated) {
            throw new NotModifiedException(getMessageService().getMessage("error.not_modified.default_message"));
        }

        log.info("Updated user");
    }
}
