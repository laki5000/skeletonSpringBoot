package com.example.user.service;

import static com.example.utils.constants.FilteringConstants.LIMIT;
import static com.example.utils.constants.FilteringConstants.PAGE;
import static com.example.utils.constants.MessageConstants.*;

import com.example.exception.ConflictException;
import com.example.exception.NotFoundException;
import com.example.exception.NotModifiedException;
import com.example.user.dto.request.UserCreateRequestDTO;
import com.example.user.dto.request.UserUpdateRequestDTO;
import com.example.user.dto.response.UserGetResponseDTO;
import com.example.user.mapper.IUserMapper;
import com.example.user.model.User;
import com.example.user.repository.IUserRepository;
import com.example.utils.dto.request.FilteringDTO;
import com.example.utils.repository.BaseSpecification;
import com.example.utils.service.IMessageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** A service class for managing user-related operations. */
@Log4j2
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final IMessageService messageService;
    private final IUserRepository userRepository;
    private final BaseSpecification<User> baseSpecification;
    private final IUserMapper userMapper;

    /**
     * Creates a new user.
     *
     * @param userCreateRequestDTO the user create request DTO containing the user's details
     * @return the user get response DTO
     * @throws ConflictException if the username already exists
     */
    @Transactional
    public UserGetResponseDTO create(UserCreateRequestDTO userCreateRequestDTO) {
        log.debug("Creating user");

        validateUsername(userCreateRequestDTO.getUsername());

        return userMapper.toGetResponseDTO(
                userRepository.save(userMapper.toEntity(userCreateRequestDTO, "unknown")));
    }

    /**
     * Gets users.
     *
     * @param filteringDTOList the search parameters
     * @return the page of user get response DTOs
     */
    public Page<UserGetResponseDTO> get(List<FilteringDTO> filteringDTOList) {
        log.debug("Getting users");

        int page = baseSpecification.getIntParamAndRemove(filteringDTOList, PAGE, 0);
        int limit = baseSpecification.getIntParamAndRemove(filteringDTOList, LIMIT, 10);
        Pageable pageable = PageRequest.of(page, limit);
        Specification<User> specification = baseSpecification.buildSpecification(filteringDTOList);

        return userRepository.findAll(specification, pageable).map(userMapper::toGetResponseDTO);
    }

    /**
     * Updates an existing user.
     *
     * @param id the id of the user to update
     * @param userUpdateRequestDTO the user update request DTO containing the user's details
     * @return the user get response DTO
     * @throws NotModifiedException if the user is not modified
     * @throws NotFoundException if the user is not found
     */
    @Transactional
    public UserGetResponseDTO update(Long id, UserUpdateRequestDTO userUpdateRequestDTO) {
        log.debug("Updating user");

        User user = getById(id);
        boolean updated = false;

        if (!user.getPassword().equals(userUpdateRequestDTO.getPassword())) {
            user.setPassword(userUpdateRequestDTO.getPassword());

            updated = true;
        }
        if (!updated) {
            throw new NotModifiedException(messageService.getMessage(ERROR_USER_NOT_MODIFIED));
        }

        user.setUpdatedBy("unknown");

        return userMapper.toGetResponseDTO(userRepository.saveAndFlush(user));
    }

    /**
     * Deletes a user.
     *
     * @param id the id of the user to delete
     * @throws NotFoundException if the user is not found
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Deleting user");

        userRepository.delete(getById(id));
    }

    /**
     * Validates that a username is unique.
     *
     * @param username the username to validate
     * @throws ConflictException if the username already exists
     */
    private void validateUsername(String username) {
        log.debug("Validating username: {}", username);

        if (userRepository.existsByUsername(username)) {
            throw new ConflictException(messageService.getMessage(ERROR_USER_USERNAME_EXISTS));
        }
    }

    /**
     * Gets a user by ID.
     *
     * @param id the ID of the user to get
     * @return the user
     * @throws NotFoundException if the user is not found
     */
    private User getById(Long id) {
        log.debug("Getting user by ID: {}", id);

        return userRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new NotFoundException(
                                        messageService.getMessage(ERROR_USER_NOT_FOUND)));
    }
}
