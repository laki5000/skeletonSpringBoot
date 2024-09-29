package com.example.domain.user.specification;

import static com.example.Constants.*;
import static com.example.TestUtils.buildFilteringDTO;
import static com.example.TestUtils.buildUser;
import static com.example.enums.FilterOperator.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.domain.user.model.User;
import com.example.domain.user.repository.IUserRepository;
import com.example.exception.InvalidDateFormatException;
import com.example.exception.InvalidFilterException;
import com.example.utils.dto.request.FilteringDTO;
import com.example.utils.service.MessageServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
public class UserSpecificationTests {
    @Autowired private UserSpecification userSpecification;
    @Autowired private MessageServiceImpl messageService;
    @Autowired private IUserRepository userRepository;

    @Test
    @DisplayName("Tests the successful creation of a specification due to a null filtering list")
    @Transactional
    void buildSpecification_NullFilteringList() {
        // Given
        saveUser();

        Specification<User> specification = userSpecification.buildSpecification(null, null, null);

        // When
        List<User> users = userRepository.findAll(specification);

        // Then
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName(
            "Tests the successful creation of a specification due to an empty filtering list after removing the password field")
    @Transactional
    void buildSpecification_EmptyFilteringListAfterRemovingPasswordField() {
        // Given
        saveUser();

        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_PASSWORD, EQUALS, TEST_PASSWORD, null)));
        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When
        List<User> users = userRepository.findAll(specification);

        // Then
        assertEquals(1, users.size());
        assertTrue(filteringDTOList.isEmpty());
    }

    @Test
    @DisplayName(
            "Tests the successful creation of a specification due to a valid String EQUALS filter")
    @Transactional
    void buildSpecification_ValidStringEqualsFilter() {
        // Given
        saveUser();

        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_USERNAME, EQUALS, TEST_USERNAME, null)));
        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When
        List<User> users = userRepository.findAll(specification);

        // Then
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName(
            "Tests the successful creation of a specification due to a valid String CONTAINS filter")
    @Transactional
    void buildSpecification_ValidStringContainsFilter() {
        // Given
        saveUser();

        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_USERNAME,
                                        CONTAINS,
                                        TEST_USERNAME.substring(1, 4),
                                        null)));
        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When
        List<User> users = userRepository.findAll(specification);

        // Then
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName(
            "Tests the successful creation of a specification due to a valid String STARTS_WITH filter")
    @Transactional
    void buildSpecification_ValidStringStartsWithFilter() {
        // Given
        saveUser();

        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_USERNAME,
                                        STARTS_WITH,
                                        TEST_USERNAME.substring(0, 3),
                                        null)));
        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When
        List<User> users = userRepository.findAll(specification);

        // Then
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName(
            "Tests the successful creation of a specification due to a valid String ENDS_WITH filter")
    @Transactional
    void buildSpecification_ValidStringEndsWithFilter() {
        // Given
        saveUser();

        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_USERNAME,
                                        ENDS_WITH,
                                        TEST_USERNAME.substring(2, 4),
                                        null)));
        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When
        List<User> users = userRepository.findAll(specification);

        // Then
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName(
            "Tests the successful creation of a specification due to a valid String NOT_EQUALS filter")
    @Transactional
    void buildSpecification_ValidStringNotEqualsFilter() {
        // Given
        saveUser();

        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_USERNAME, NOT_EQUALS, TEST_USERNAME2, null)));
        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When
        List<User> users = userRepository.findAll(specification);

        // Then
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName(
            "Tests the successful creation of a specification due to a valid Date EQUALS filter")
    @Transactional
    void buildSpecification_ValidDateEqualsFilter() {
        // Given
        User user = saveUser();
        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_CREATED_AT,
                                        EQUALS,
                                        user.getCreatedAt().toString(),
                                        null)));
        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When
        List<User> users = userRepository.findAll(specification);

        // Then
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName(
            "Tests the successful creation of a specification due to a valid Date GREATER_THAN filter")
    @Transactional
    void buildSpecification_ValidDateGreaterThanFilter() {
        // Given
        User user = saveUser();
        String dateStringBefore = user.getCreatedAt().minusSeconds(1).toString();
        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_CREATED_AT,
                                        GREATER_THAN,
                                        dateStringBefore,
                                        null)));
        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When
        List<User> users = userRepository.findAll(specification);

        // Then
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName(
            "Tests the successful creation of a specification due to a valid Date LESS_THAN filter")
    @Transactional
    void buildSpecification_ValidDateLessThanFilter() {
        // Given
        User user = saveUser();
        String dateStringAfter = user.getCreatedAt().plusSeconds(1).toString();
        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_CREATED_AT, LESS_THAN, dateStringAfter, null)));
        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When
        List<User> users = userRepository.findAll(specification);

        // Then
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName(
            "Tests the successful creation of a specification due to a valid Date BETWEEN filter")
    @Transactional
    void buildSpecification_ValidDateBetweenFilter() {
        // Given
        User user = saveUser();
        String dateStringBefore = user.getCreatedAt().minusSeconds(1).toString();
        String dateStringAfter = user.getCreatedAt().plusSeconds(1).toString();
        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_CREATED_AT,
                                        BETWEEN,
                                        dateStringBefore,
                                        dateStringAfter)));
        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When
        List<User> users = userRepository.findAll(specification);

        // Then
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName(
            "Tests the successful creation of a specification due to a valid Date NOT_EQUALS filter")
    @Transactional
    void buildSpecification_ValidDateNotEqualsFilter() {
        // Given
        User user = saveUser();
        String dateStringBefore = user.getCreatedAt().minusSeconds(1).toString();
        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_CREATED_AT,
                                        NOT_EQUALS,
                                        dateStringBefore,
                                        null)));
        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When
        List<User> users = userRepository.findAll(specification);

        // Then
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName(
            "Tests the successful creation of a specification due to a valid Long EQUALS filter")
    @Transactional
    void buildSpecification_ValidLongEqualsFilter() {
        // Given
        User user = saveUser();

        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_ID,
                                        EQUALS,
                                        String.valueOf(user.getId()),
                                        null)));
        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When
        List<User> users = userRepository.findAll(specification);

        // Then
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName(
            "Tests the successful creation of a specification due to a valid Long GREATER_THAN filter")
    @Transactional
    void buildSpecification_ValidLongGreaterThanFilter() {
        // Given
        User user = saveUser();

        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_ID,
                                        GREATER_THAN,
                                        String.valueOf(user.getId() - 1),
                                        null)));
        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When
        List<User> users = userRepository.findAll(specification);

        // Then
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName(
            "Tests the successful creation of a specification due to a valid Long LESS_THAN filter")
    @Transactional
    void buildSpecification_ValidLongLessThanFilter() {
        // Given
        User user = saveUser();

        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_ID,
                                        LESS_THAN,
                                        String.valueOf(user.getId() + 1),
                                        null)));
        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When
        List<User> users = userRepository.findAll(specification);

        // Then
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName(
            "Tests the successful creation of a specification due to a valid Long BETWEEN filter")
    @Transactional
    void buildSpecification_ValidLongBetweenFilter() {
        // Given
        User user = saveUser();

        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_ID,
                                        BETWEEN,
                                        String.valueOf(user.getId() - 1),
                                        String.valueOf(user.getId() + 1))));
        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When
        List<User> users = userRepository.findAll(specification);

        // Then
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName(
            "Tests the successful creation of a specification due to a valid Long NOT_EQUALS filter")
    @Transactional
    void buildSpecification_ValidLongNotEqualsFilter() {
        // Given
        User user = saveUser();

        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_ID,
                                        NOT_EQUALS,
                                        String.valueOf(user.getId() + 1),
                                        null)));
        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When
        List<User> users = userRepository.findAll(specification);

        // Then
        assertEquals(1, users.size());
    }

    @Test
    @DisplayName("Tests the unsuccessful creation of a specification due to invalid date format")
    void buildSpecification_InvalidDateFormat() {
        // Given
        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_CREATED_AT, EQUALS, TEST_INVALID_DATE, null)));

        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When & Then
        assertThrows(InvalidDateFormatException.class, () -> userRepository.findAll(specification));
    }

    @Test
    @DisplayName(
            "Tests the unsuccessful creation of a specification due to invalid filter with null value")
    void buildSpecification_NullValueFilter() {
        // Given
        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(List.of(buildFilteringDTO(null, null, null, null)));

        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When & Then
        assertThrows(InvalidFilterException.class, () -> userRepository.findAll(specification));
    }

    @Test
    @DisplayName(
            "Tests the unsuccessful creation of a specification due to invalid filter with illegal String operator")
    void buildSpecification_IllegalStringOperator() {
        // Given
        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_CREATED_BY, GREATER_THAN, TEST_USERNAME, null)));

        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When & Then
        assertThrows(InvalidFilterException.class, () -> userRepository.findAll(specification));
    }

    @Test
    @DisplayName(
            "Tests the unsuccessful creation of a specification due to invalid filter with null other value in Date BETWEEN operator")
    void buildSpecification_NullOtherValueInDateBetweenOperator() {
        // Given
        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_CREATED_AT, BETWEEN, TEST_DATE, null)));

        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When & Then
        assertThrows(InvalidFilterException.class, () -> userRepository.findAll(specification));
    }

    @Test
    @DisplayName(
            "Tests the unsuccessful creation of a specification due to invalid filter with illegal Date operator")
    void buildSpecification_IllegalDateOperator() {
        // Given
        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_CREATED_AT, CONTAINS, TEST_DATE, null)));

        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When & Then
        assertThrows(InvalidFilterException.class, () -> userRepository.findAll(specification));
    }

    @Test
    @DisplayName(
            "Tests the unsuccessful creation of a specification due to invalid filter with null other value in Long BETWEEN operator")
    void buildSpecification_NullOtherValueInLongBetweenOperator() {
        // Given
        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_ID, BETWEEN, String.valueOf(TEST_ID), null)));

        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When & Then
        assertThrows(InvalidFilterException.class, () -> userRepository.findAll(specification));
    }

    @Test
    @DisplayName(
            "Tests the unsuccessful creation of a specification due to invalid filter with illegal Long operator")
    void buildSpecification_IllegalLongOperator() {
        // Given
        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_ID, CONTAINS, String.valueOf(TEST_ID), null)));

        Specification<User> specification =
                userSpecification.buildSpecification(filteringDTOList, null, null);

        // When & Then
        assertThrows(InvalidFilterException.class, () -> userRepository.findAll(specification));
    }

    @Test
    @DisplayName("Tests the successful removal of a parameter")
    void removeParam_Success() {
        // Given
        List<FilteringDTO> filteringDTOList =
                new ArrayList<>(
                        List.of(
                                buildFilteringDTO(
                                        TEST_FIELD_CREATED_BY, EQUALS, TEST_USERNAME, null)));

        // When
        userSpecification.removeParam(filteringDTOList, TEST_FIELD_CREATED_BY);

        // Then
        assertTrue(filteringDTOList.isEmpty());
    }

    /** Saves a user with the test properties. */
    private User saveUser() {
        return userRepository.save(
                buildUser(
                        null,
                        TEST_USERNAME,
                        TEST_PASSWORD,
                        null,
                        null,
                        TEST_USERNAME,
                        TEST_USERNAME,
                        null,
                        TEST_FIRST_NAME,
                        TEST_LAST_NAME,
                        null,
                        null,
                        TEST_USERNAME,
                        TEST_USERNAME));
    }
}
