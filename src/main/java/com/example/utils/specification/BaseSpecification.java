package com.example.utils.specification;

import static com.example.utils.constants.FilteringConstants.*;
import static com.example.utils.constants.MessageConstants.ERROR_INVALID_DATE_FORMAT;
import static com.example.utils.constants.MessageConstants.ERROR_INVALID_FILTER;

import com.example.exception.InvalidDateFormatException;
import com.example.exception.InvalidFilterException;
import com.example.utils.dto.request.FilteringDTO;
import com.example.utils.enums.FilterOperator;
import com.example.utils.service.IMessageService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * Base specification class.
 *
 * @param <T> the entity type
 */
@Component
@RequiredArgsConstructor
public class BaseSpecification<T> {
    private final IMessageService messageService;

    /**
     * Build a specification.
     *
     * @param filteringDTOList the search parameters
     * @param orderBy the field to order by
     * @param orderDirection the direction to order by
     * @return the specification
     * @throws InvalidDateFormatException if the date format is invalid
     * @throws InvalidFilterException if the filter is invalid
     */
    public Specification<T> buildSpecification(
            List<FilteringDTO> filteringDTOList, String orderBy, String orderDirection) {
        removeParam(filteringDTOList, PASSWORD);

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
                    throw new InvalidFilterException(
                            messageService.getMessage(ERROR_INVALID_FILTER));
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
                    default -> throw new InvalidFilterException(
                            messageService.getMessage(ERROR_INVALID_FILTER));
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
            default -> throw new InvalidFilterException(
                    messageService.getMessage(ERROR_INVALID_FILTER));
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
                    throw new InvalidFilterException(
                            messageService.getMessage(ERROR_INVALID_FILTER));
                }

                Instant parsedOtherValue = parseDate(otherValue);

                return criteriaBuilder.between(root.get(key), parsedValue, parsedOtherValue);
            case NOT_EQUALS:
                return criteriaBuilder.notEqual(root.get(key), parsedValue);
            default:
                throw new InvalidFilterException(messageService.getMessage(ERROR_INVALID_FILTER));
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
                    throw new InvalidFilterException(
                            messageService.getMessage(ERROR_INVALID_FILTER));
                }

                Long parsedOtherValue = Long.parseLong(otherValue);

                return criteriaBuilder.between(root.get(key), parsedValue, parsedOtherValue);
            case NOT_EQUALS:
                return criteriaBuilder.notEqual(root.get(key), parsedValue);
            default:
                throw new InvalidFilterException(messageService.getMessage(ERROR_INVALID_FILTER));
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
            throw new InvalidDateFormatException(
                    messageService.getMessage(ERROR_INVALID_DATE_FORMAT));
        }
    }
}
