package com.example.user.service;

import com.example.exception.ConflictException;
import com.example.exception.NotFoundException;
import com.example.exception.NotModifiedException;
import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.request.UserUpdateRequestDTO;
import com.example.user.dto.response.UserGetResponseDTO;
import com.example.user.mapper.UserMapper;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import com.example.utils.service.BaseService;
import com.example.utils.service.MessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/** A service class for managing users. */
@Log4j2
@Service
public class UserService extends BaseService<User, UserCreateRequestDTO, UserUpdateRequestDTO, UserGetResponseDTO> {
    /**
     * Constructor.
     *
     * @param messageService the message service
     * @param userRepository the user repository
     * @param userMapper the user mapper
     */
    public UserService(MessageService messageService, UserRepository userRepository, UserMapper userMapper) {
        super(messageService, userRepository, userMapper, User.class);
    }

    /**
     * Validate the user create request.
     *
     * @param entity the user create request
     */
    @Override
    protected void validateCreate(UserCreateRequestDTO entity) {
        log.info("Validating user create request");

        existsByUsername(entity.getUsername());

        log.info("User create request is valid");
    }

    /**
     * Validate the user update request.
     *
     * @param update the entity to validate
     * @return the updated user
     */
    @Override
    protected User validateUpdate(UserUpdateRequestDTO update) {
        log.info("Validating user update request");

        User user = findById(update.getId());

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

        findById(id);

        log.info("User delete request is valid");
    }

    /**
     * Check if a user exists by username.
     *
     * @param username the username
     */
    public void existsByUsername(String username) {
        log.info("Checking if user exists by username: {}", username);

        if (getRepository().existsByUsername(username)) {
            throw new ConflictException(getMessageService().getMessage("error.conflict.username_exists"));
        }

        log.info("User does not exist by username: {}", username);
    }

    /**
     * Validate the user by id.
     *
     * @param id the user id
     * @return the user if the id is valid, an error message otherwise
     */
    public User findById(Long id) {
        log.info("Validating user by id: {}", id);

        User user = super.getRepository().findById(id).orElseThrow(() -> new NotFoundException(getMessageService().getMessage("error.not_found.user")));

        log.info("User found with id: {}", id);

        return user;
    }

    /**
     * Get the id from the user update request.
     *
     * @param update the user update request
     * @return the id
     */
    @Override
    protected Long getIdFromUpdateRequest(UserUpdateRequestDTO update) {
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
    protected void doUpdate(User entity, UserUpdateRequestDTO update) {
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
