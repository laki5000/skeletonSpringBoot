package com.example.domain.user.service;

import static com.example.utils.constants.MessageConstants.*;
import static com.example.utils.constants.SuppressionConstants.EI_EXPOSE_REP2;
import static com.example.utils.constants.SuppressionConstants.EI_EXPOSE_REP2_JUSTIFICATION;

import com.example.domain.user.dto.request.UserCreateRequestDTO;
import com.example.domain.user.dto.request.UserUpdateRequestDTO;
import com.example.domain.user.dto.response.UserGetResponseDTO;
import com.example.domain.user.mapper.IUserMapper;
import com.example.domain.user.model.User;
import com.example.domain.user.repository.IUserRepository;
import com.example.domain.user.specification.UserSpecification;
import com.example.exception.ConflictException;
import com.example.exception.NotFoundException;
import com.example.exception.NotModifiedException;
import com.example.utils.dto.request.FilteringDTO;
import com.example.utils.service.IMessageService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
@SuppressFBWarnings(value = EI_EXPOSE_REP2, justification = EI_EXPOSE_REP2_JUSTIFICATION)
public class UserServiceImpl implements IUserService {
    private final IMessageService messageService;
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;
    private final UserSpecification userSpecification;

    /**
     * Creates a new user.
     *
     * @param userCreateRequestDTO the DTO containing the user's details
     * @return the response DTO
     * @throws ConflictException if the username already exists
     */
    @Override
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
     * @param page the page number
     * @param limit the number of users per page
     * @param orderBy the field to order by
     * @param orderDirection the direction to order by
     * @param filteringDTOList the search parameters
     * @return the page of response DTOs
     */
    @Override
    public Page<UserGetResponseDTO> get(
            int page,
            int limit,
            String orderBy,
            String orderDirection,
            List<FilteringDTO> filteringDTOList) {
        log.debug("Getting users");
        Pageable pageable = PageRequest.of(page, limit);
        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, orderBy, orderDirection);

        return userRepository.findAll(specification, pageable).map(userMapper::toGetResponseDTO);
    }

    /**
     * Updates an existing user.
     *
     * @param id the id of the user to update
     * @param userUpdateRequestDTO the DTO containing the user's details
     * @return the response DTO
     * @throws NotModifiedException if the user is not modified
     * @throws NotFoundException if the user is not found
     */
    @Override
    @Transactional
    public UserGetResponseDTO update(Long id, UserUpdateRequestDTO userUpdateRequestDTO) {
        log.debug("Updating user");

        User user = getById(id);
        boolean updated = false;

        if (userUpdateRequestDTO.getPassword() != null
                && !user.getPassword().equals(userUpdateRequestDTO.getPassword())) {
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
    @Override
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
     * @return the entity
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
