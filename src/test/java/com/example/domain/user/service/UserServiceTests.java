package com.example.domain.user.service;

import static com.example.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.domain.user.dto.request.UserCreateRequestDTO;
import com.example.domain.user.dto.request.UserDetailsRequestDTO;
import com.example.domain.user.dto.request.UserUpdateRequestDTO;
import com.example.domain.user.dto.response.UserResponseDTO;
import com.example.domain.user.mapper.IUserMapper;
import com.example.domain.user.model.User;
import com.example.domain.user.model.UserDetails;
import com.example.domain.user.repository.IUserRepository;
import com.example.domain.user.specification.UserSpecification;
import com.example.exception.ConflictException;
import com.example.exception.NotFoundException;
import com.example.exception.NotModifiedException;
import com.example.utils.dto.request.FilteringDTO;
import com.example.utils.service.IMessageService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/** Unit tests for {@link UserServiceImpl}. */
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @InjectMocks private UserServiceImpl userService;
    @Mock private IMessageService messageService;
    @Mock private IUserDetailsService userDetailsService;
    @Mock private IUserRepository userRepository;
    @Mock private IUserMapper userMapper;
    @Mock private UserSpecification specification;

    @Test
    @DisplayName("Tests the successful creation of a user.")
    void create_Success() {
        // Given
        UserDetailsRequestDTO userDetailsRequestDTO = UserDetailsRequestDTO.builder().build();
        UserCreateRequestDTO userCreateRequestDTO =
                UserCreateRequestDTO.builder()
                        .username(TEST_USERNAME)
                        .details(userDetailsRequestDTO)
                        .build();
        UserDetails userDetails = UserDetails.builder().build();
        User user = User.builder().details(userDetails).build();
        UserResponseDTO userResponseDTO = UserResponseDTO.builder().build();

        when(userRepository.existsByUsername(TEST_USERNAME)).thenReturn(false);
        when(userMapper.toEntity(userCreateRequestDTO, "unknown")).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        // When
        UserResponseDTO result = userService.create(userCreateRequestDTO);

        // Then
        assertEquals(userResponseDTO, result);

        verify(userRepository).existsByUsername(TEST_USERNAME);
        verify(userMapper).toEntity(userCreateRequestDTO, "unknown");
        verify(userRepository).save(user);
        verify(userMapper).toResponseDTO(user);
    }

    @Test
    @DisplayName("Tests the unsuccessful creation of a user due to username exists.")
    void create_UsernameExists() {
        // Given
        UserCreateRequestDTO userCreateRequestDTO =
                UserCreateRequestDTO.builder().username(TEST_USERNAME).build();

        when(userRepository.existsByUsername(TEST_USERNAME)).thenReturn(true);

        // When & Then
        assertThrows(ConflictException.class, () -> userService.create(userCreateRequestDTO));

        verify(userRepository).existsByUsername(TEST_USERNAME);
    }

    @Test
    @DisplayName("Tests the successful retrieval of users.")
    void get_Success() {
        // Given
        List<FilteringDTO> filteringDTOList = List.of(FilteringDTO.builder().build());
        Pageable pageable = PageRequest.of(TEST_PAGE, TEST_LIMIT);

        @SuppressWarnings("unchecked")
        Specification<User> specificationMock = mock(Specification.class);

        UserDetails userDetails = UserDetails.builder().build();
        User user = User.builder().details(userDetails).build();
        UserResponseDTO userResponseDTO = UserResponseDTO.builder().build();

        when(specification.buildSpecification(
                        filteringDTOList, TEST_ORDER_BY, TEST_ORDER_DIRECTION))
                .thenReturn(specificationMock);
        when(userRepository.findAll(specificationMock, pageable))
                .thenReturn(new PageImpl<>(List.of(user)));
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        // When
        Page<UserResponseDTO> result =
                userService.get(
                        TEST_PAGE,
                        TEST_LIMIT,
                        TEST_ORDER_BY,
                        TEST_ORDER_DIRECTION,
                        filteringDTOList);

        // Then
        assertEquals(List.of(userResponseDTO), result.getContent());

        verify(specification)
                .buildSpecification(filteringDTOList, TEST_ORDER_BY, TEST_ORDER_DIRECTION);
        verify(userRepository).findAll(specificationMock, pageable);
        verify(userMapper).toResponseDTO(user);
    }

    @Test
    @DisplayName("Tests the successful update of a user.")
    void update_Success() {
        // Given
        UserDetailsRequestDTO userDetailsRequestDTO = UserDetailsRequestDTO.builder().build();
        UserUpdateRequestDTO userUpdateRequestDTO =
                UserUpdateRequestDTO.builder()
                        .password(TEST_PASSWORD2)
                        .details(userDetailsRequestDTO)
                        .build();
        UserDetails userDetails = UserDetails.builder().build();
        User user = User.builder().password(TEST_PASSWORD).details(userDetails).build();
        UserResponseDTO userResponseDTO = UserResponseDTO.builder().build();

        when(userRepository.findById(TEST_ID)).thenReturn(Optional.of(user));
        when(userDetailsService.updateUserDetails(userDetails, userDetailsRequestDTO))
                .thenReturn(true);
        doNothing().when(userDetailsService).updateAuditFields(userDetails, true);
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        // When
        UserResponseDTO result = userService.update(TEST_ID, userUpdateRequestDTO);

        // Then
        assertEquals(userResponseDTO, result);

        verify(userRepository).findById(TEST_ID);
        verify(userDetailsService).updateUserDetails(userDetails, userDetailsRequestDTO);
        verify(userDetailsService).updateAuditFields(userDetails, true);
        verify(userRepository).saveAndFlush(user);
        verify(userMapper).toResponseDTO(user);
    }

    @Test
    @DisplayName("Tests the unsuccessful update of a user due to not found.")
    void update_NotFound() {
        // Given
        UserUpdateRequestDTO userUpdateRequestDTO =
                UserUpdateRequestDTO.builder().password(TEST_PASSWORD).build();

        when(userRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(
                NotFoundException.class, () -> userService.update(TEST_ID, userUpdateRequestDTO));

        verify(userRepository).findById(TEST_ID);
    }

    @Test
    @DisplayName("Tests the unsuccessful update of a user due to not modified.")
    void update_NotModified() {
        // Given
        UserDetailsRequestDTO userDetailsRequestDTO = UserDetailsRequestDTO.builder().build();
        UserUpdateRequestDTO userUpdateRequestDTO =
                UserUpdateRequestDTO.builder()
                        .password(TEST_PASSWORD)
                        .details(userDetailsRequestDTO)
                        .build();
        User user = User.builder().password(TEST_PASSWORD).build();

        when(userRepository.findById(TEST_ID)).thenReturn(Optional.of(user));
        when(userDetailsService.updateUserDetails(user.getDetails(), userDetailsRequestDTO))
                .thenReturn(false);

        // When & Then
        assertThrows(
                NotModifiedException.class,
                () -> userService.update(TEST_ID, userUpdateRequestDTO));

        verify(userRepository).findById(TEST_ID);
        verify(userDetailsService).updateUserDetails(user.getDetails(), userDetailsRequestDTO);
    }

    @Test
    @DisplayName("Tests the successful deletion of a user.")
    void delete_Success() {
        // Given
        User user = User.builder().build();

        when(userRepository.findById(TEST_ID)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        // When
        userService.delete(TEST_ID);

        // Then
        verify(userRepository).findById(TEST_ID);
        verify(userRepository).delete(user);
    }

    @Test
    @DisplayName("Tests the unsuccessful deletion of a user due to not found.")
    void delete_NotFound() {
        // Given
        when(userRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> userService.delete(TEST_ID));

        verify(userRepository).findById(TEST_ID);
    }
}
