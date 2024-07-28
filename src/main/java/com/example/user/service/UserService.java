package com.example.user.service;

import com.example.exception.ConflictException;
import com.example.exception.NotFoundException;
import com.example.exception.NotModifiedException;
import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.request.UserUpdateRequestDTO;
import com.example.user.dto.response.UserGetResponseDTO;
import com.example.user.mapper.IUserMapper;
import com.example.user.model.User;
import com.example.user.repository.IUserRepository;
import com.example.utils.service.IMessageService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** A service class for managing user-related operations. */
@Log4j2
@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final IMessageService messageService;
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;

    /**
     * Creates a new user.
     *
     * @param userCreateRequestDTO the user create request DTO containing the user's details
     * @return the user get response DTO
     */
    @Transactional
    public UserGetResponseDTO create(UserCreateRequestDTO userCreateRequestDTO) {
        log.info("Creating user");

        ensureUsernameIsUnique(userCreateRequestDTO.getUsername());

        return userMapper.toGetResponseDTO(
                userRepository.save(userMapper.toEntity(userCreateRequestDTO, "unknown")));
    }

    /**
     * Updates an existing user.
     *
     * @param userUpdateRequestDTO the user update request DTO containing the user's details
     * @return the user get response DTO
     * @throws NotModifiedException if the user is not modified
     */
    @Transactional
    public UserGetResponseDTO update(UserUpdateRequestDTO userUpdateRequestDTO) {
        log.info("Updating user");

        User user = getById(userUpdateRequestDTO.getId());
        boolean updated = false;

        if (!user.getPassword().equals(userUpdateRequestDTO.getPassword())) {
            user.setPassword(userUpdateRequestDTO.getPassword());

            updated = true;
        }
        if (!updated) {
            throw new NotModifiedException(messageService.getMessage("error.user.not_modified"));
        }

        user.setUpdatedBy("unknown");

        return userMapper.toGetResponseDTO(userRepository.saveAndFlush(user));
    }

    /**
     * Deletes a user.
     *
     * @param id the id of the user to delete
     */
    @Transactional
    public void delete(Long id) {
        log.info("Deleting user");

        userRepository.delete(getById(id));
    }

    /**
     * Gets users.
     *
     * @param params the search parameters
     * @return the page of user get response DTOs
     */
    public Page<UserGetResponseDTO> get(Map<String, String> params) {
        log.info("Getting users");

        return userRepository.findAllWithCriteria(params).map(userMapper::toGetResponseDTO);
    }

    /**
     * Ensures that a username is unique.
     *
     * @param username the username to validate
     * @throws ConflictException if the username already exists
     */
    public void ensureUsernameIsUnique(String username) {
        log.info("Validating username: {}", username);

        if (existsByUsername(username)) {
            throw new ConflictException(messageService.getMessage("error.user.username_exists"));
        }
    }

    /**
     * Checks if a user exists by username.
     *
     * @param username the username to check
     * @return true if the user exists, false otherwise
     */
    public boolean existsByUsername(String username) {
        log.info("Checking if user exists by username: {}", username);

        return userRepository.existsByUsername(username);
    }

    /**
     * Gets a user by ID.
     *
     * @param id the ID of the user to get
     * @return the user
     * @throws NotFoundException if the user is not found
     */
    public User getById(Long id) {
        log.info("Getting user by ID: {}", id);

        return userRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new NotFoundException(
                                        messageService.getMessage("error.user.not_found")));
    }
}
