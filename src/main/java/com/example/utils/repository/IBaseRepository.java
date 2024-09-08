package com.example.utils.repository;

import com.example.exception.InvalidDateFormatException;
import com.example.exception.InvalidFilterException;
import com.example.utils.dto.request.FilteringDTO;
import com.example.utils.enums.FilterOperator;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base repository interface.
 *
 * @param <T> the entity type to manage
 * @param <ID> the ID type of the entity
 */
@NoRepositoryBean
public interface IBaseRepository<T, ID extends Serializable>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    String PAGE = "page";
    String LIMIT = "limit";
    String ORDER_BY = "orderBy";
    String ORDER_DIRECTION = "orderDirection";
    String ASC = "asc";
    String DESC = "desc";
    String PASSWORD = "password";
    String ID = "id";

    String STRING = "String";
    String INSTANT = "Instant";
    String LONG = "Long";

    String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    String DATE_TIME_REGEX = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}";
    String DATE_TIME_WITH_MILLIS_REGEX = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}Z";
    String UTC = "UTC";

    String INVALID_FILTER_MESSAGE = "Invalid filter";
    String INVALID_DATE_FORMAT_MESSAGE =
            "expected formats: yyyy-MM-dd, yyyy-MM-ddTHH:mm:ss, yyyy-MM-ddTHH:mm:ss.SSSSSSZ, received: ";

    /**
     * Find all entities with criteria.
     *
     * @param filteringDTOList the search parameters
     * @return page of entities
     * @throws InvalidDateFormatException if the date format is invalid
     * @throws InvalidFilterException if the filter is invalid
     */
    default Page<T> findAllWithCriteria(List<FilteringDTO> filteringDTOList) {
        int page = getIntParamAndRemove(filteringDTOList, PAGE, 0);
        int limit = getIntParamAndRemove(filteringDTOList, LIMIT, 10);
        String orderBy = getParamAndRemove(filteringDTOList, ORDER_BY, ID);
        String orderDirection = getParamAndRemove(filteringDTOList, ORDER_DIRECTION, ASC);

        removeParam(filteringDTOList, PASSWORD);

        Pageable pageable = PageRequest.of(page, limit);
        Specification<T> spec = buildSpecification(filteringDTOList, orderBy, orderDirection);

        return findAll(spec, pageable);
    }

    /**
     * Get an integer parameter and remove it from the list.
     *
     * @param filteringDTOList the search parameters
     * @param key the parameter key
     * @param defaultValue the default value
     * @return the integer value
     */
    private int getIntParamAndRemove(
            List<FilteringDTO> filteringDTOList, String key, int defaultValue) {
        return filteringDTOList == null
                ? defaultValue
                : filteringDTOList.stream()
                        .filter(filteringDTO -> key.equals(filteringDTO.getField()))
                        .findFirst()
                        .map(
                                filteringDTO -> {
                                    removeParam(filteringDTOList, key);

                                    return Integer.parseInt(filteringDTO.getValue());
                                })
                        .orElse(defaultValue);
    }

    /**
     * Get a string parameter and remove it from the list.
     *
     * @param filteringDTOList the search parameters
     * @param key the parameter key
     * @param defaultValue the default value
     * @return the string value
     */
    private String getParamAndRemove(
            List<FilteringDTO> filteringDTOList, String key, String defaultValue) {
        return filteringDTOList == null
                ? defaultValue
                : filteringDTOList.stream()
                        .filter(filteringDTO -> key.equals(filteringDTO.getField()))
                        .findFirst()
                        .map(
                                filteringDTO -> {
                                    removeParam(filteringDTOList, key);

                                    return filteringDTO.getValue();
                                })
                        .orElse(defaultValue);
    }

    /**
     * Remove a parameter from the list.
     *
     * @param filteringDTOList the search parameters
     * @param key the parameter key
     */
    private void removeParam(List<FilteringDTO> filteringDTOList, String key) {
        if (filteringDTOList != null) {
            filteringDTOList.removeIf(filteringDTO -> key.equals(filteringDTO.getField()));
        }
    }

    /**
     * Build a specification.
     *
     * @param filteringDTOList the search parameters
     * @param orderBy the field to order by
     * @param orderDirection the order direction
     * @return the specification
     * @throws InvalidDateFormatException if the date format is invalid
     * @throws InvalidFilterException if the filter is invalid
     */
    private Specification<T> buildSpecification(
            List<FilteringDTO> filteringDTOList, String orderBy, String orderDirection) {
        return (root, query, criteriaBuilder) -> {
            if (filteringDTOList == null) {
                return criteriaBuilder.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();

            for (FilteringDTO filter : filteringDTOList) {
                String key = filter.getField();
                String value = filter.getValue();
                FilterOperator operator = filter.getOperator();
                String otherValue = filter.getOtherValue();

                if (isNullOrEmpty(value) || isNullOrEmpty(key) || operator == null) {
                    throw new InvalidFilterException(INVALID_FILTER_MESSAGE);
                }

                Class<?> javaType = root.get(key).getJavaType();

                switch (javaType.getSimpleName()) {
                    case STRING -> predicates.add(
                            buildStringPredicate(criteriaBuilder, root, key, value, operator));
                    case INSTANT -> predicates.add(
                            buildDatePredicate(
                                    criteriaBuilder, root, key, value, operator, otherValue));
                    case LONG -> predicates.add(
                            buildLongPredicate(
                                    criteriaBuilder, root, key, value, operator, otherValue));
                    default -> throw new InvalidFilterException(INVALID_FILTER_MESSAGE);
                }
            }

            if (orderBy != null && orderDirection != null) {
                if (ASC.equalsIgnoreCase(orderDirection)) {
                    query.orderBy(criteriaBuilder.asc(root.get(orderBy)));
                } else if (DESC.equalsIgnoreCase(orderDirection)) {
                    query.orderBy(criteriaBuilder.desc(root.get(orderBy)));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Build a predicate for a string field.
     *
     * @param criteriaBuilder the criteria builder
     * @param root the root
     * @param key the field key
     * @param value the value
     * @param operator the operator
     * @return the predicate
     * @throws InvalidFilterException if the filter is invalid
     */
    private Predicate buildStringPredicate(
            CriteriaBuilder criteriaBuilder,
            Root<T> root,
            String key,
            String value,
            FilterOperator operator) {
        return switch (operator) {
            case EQUALS -> criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get(key)), value.toLowerCase());
            case CONTAINS -> criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(key)), "%" + value.toLowerCase() + "%");
            case STARTS_WITH -> criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(key)), value.toLowerCase() + "%");
            case ENDS_WITH -> criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(key)), "%" + value.toLowerCase());
            case NOT_EQUALS -> criteriaBuilder.notEqual(
                    criteriaBuilder.lower(root.get(key)), value.toLowerCase());
            default -> throw new InvalidFilterException(INVALID_FILTER_MESSAGE);
        };
    }

    /**
     * Build a predicate for a date field.
     *
     * @param criteriaBuilder the criteria builder
     * @param root the root
     * @param key the field key
     * @param value the value
     * @param operator the operator
     * @param otherValue the other value
     * @return the predicate
     * @throws InvalidFilterException if the filter is invalid
     * @throws InvalidDateFormatException if the date format is invalid
     */
    private Predicate buildDatePredicate(
            CriteriaBuilder criteriaBuilder,
            Root<T> root,
            String key,
            String value,
            FilterOperator operator,
            String otherValue)
            throws InvalidFilterException {
        Instant parsedValue = parseDate(value);

        switch (operator) {
            case EQUALS:
                return criteriaBuilder.equal(root.get(key), parsedValue);
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(root.get(key), parsedValue);
            case LESS_THAN:
                return criteriaBuilder.lessThan(root.get(key), parsedValue);
            case BETWEEN:
                if (isNullOrEmpty(otherValue)) {
                    throw new InvalidFilterException(INVALID_FILTER_MESSAGE);
                }

                Instant parsedOtherValue = parseDate(otherValue);

                return criteriaBuilder.between(root.get(key), parsedValue, parsedOtherValue);
            case NOT_EQUALS:
                return criteriaBuilder.notEqual(root.get(key), parsedValue);
            default:
                throw new InvalidFilterException(INVALID_FILTER_MESSAGE);
        }
    }

    /**
     * Check if a string is null or empty.
     *
     * @param value the string to check
     * @return true if the string is null or empty, false otherwise
     */
    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    /**
     * Build a predicate for a long field.
     *
     * @param criteriaBuilder the criteria builder
     * @param root the root
     * @param key the field key
     * @param value the value
     * @param operator the operator
     * @param otherValue the other value
     * @return the predicate
     * @throws InvalidFilterException if the filter is invalid
     */
    private Predicate buildLongPredicate(
            CriteriaBuilder criteriaBuilder,
            Root<T> root,
            String key,
            String value,
            FilterOperator operator,
            String otherValue)
            throws InvalidFilterException {
        Long parsedValue = Long.parseLong(value);
        switch (operator) {
            case EQUALS:
                return criteriaBuilder.equal(root.get(key), parsedValue);
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(root.get(key), parsedValue);
            case LESS_THAN:
                return criteriaBuilder.lessThan(root.get(key), parsedValue);
            case BETWEEN:
                if (isNullOrEmpty(otherValue)) {
                    throw new InvalidFilterException(INVALID_FILTER_MESSAGE);
                }

                Long parsedOtherValue = Long.parseLong(otherValue);

                return criteriaBuilder.between(root.get(key), parsedValue, parsedOtherValue);
            case NOT_EQUALS:
                return criteriaBuilder.notEqual(root.get(key), parsedValue);
            default:
                throw new InvalidFilterException(INVALID_FILTER_MESSAGE);
        }
    }

    /**
     * Parse a date string into an Instant.
     *
     * @param value the date string
     * @return the date as an Instant
     * @throws InvalidDateFormatException if the date format is invalid
     */
    private Instant parseDate(String value) throws InvalidDateFormatException {
        if (value.matches(DATE_REGEX)) {
            return LocalDateTime.parse(value + "T00:00:00").atZone(ZoneId.of(UTC)).toInstant();
        } else if (value.matches(DATE_TIME_REGEX)) {
            return LocalDateTime.parse(value).atZone(ZoneId.of(UTC)).toInstant();
        } else if (value.matches(DATE_TIME_WITH_MILLIS_REGEX)) {
            return ZonedDateTime.parse(value).toInstant();
        } else {
            throw new InvalidDateFormatException(INVALID_DATE_FORMAT_MESSAGE + value);
        }
    }
}
