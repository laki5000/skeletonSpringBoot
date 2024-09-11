package com.example.utils.specification;

import static com.example.utils.constants.FilteringConstants.*;
import static com.example.utils.constants.MessageConstants.ERROR_INVALID_DATE_FORMAT;
import static com.example.utils.constants.MessageConstants.ERROR_INVALID_FILTER;

import com.example.exception.InvalidDateFormatException;
import com.example.exception.InvalidFilterException;
import com.example.utils.dto.request.FilteringDTO;
import com.example.utils.enums.FilterOperator;
import com.example.utils.service.IMessageService;
import jakarta.persistence.criteria.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;

/**
 * Abstract specification class for filtering entities.
 *
 * @param <T> the entity type
 */
@Log4j2
@RequiredArgsConstructor
public abstract class BaseSpecificationImpl<T> implements IBaseSpecification<T> {
    protected final IMessageService messageService;

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
    @Override
    public Specification<T> buildSpecification(
            List<FilteringDTO> filteringDTOList, String orderBy, String orderDirection) {
        log.debug("buildSpecification called");

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

                Path<?> path = getNestedField(root, key);
                Class<?> javaType = path.getJavaType();

                switch (javaType.getSimpleName()) {
                    case STRING -> predicates.add(
                            buildStringPredicate(criteriaBuilder, path, value, operator));
                    case INSTANT -> predicates.add(
                            buildDatePredicate(criteriaBuilder, path, value, operator, otherValue));
                    case LONG -> predicates.add(
                            buildLongPredicate(criteriaBuilder, path, value, operator, otherValue));
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
    @Override
    public void removeParam(List<FilteringDTO> filteringDTOList, String key) {
        log.debug("removeParam called");

        if (filteringDTOList != null) {
            filteringDTOList.removeIf(filteringDTO -> key.equals(filteringDTO.getField()));
        }
    }

    /**
     * Check if a string is null or empty.
     *
     * @param value the string to check
     * @return true if the string is null or empty, false otherwise
     */
    protected boolean isNullOrEmpty(String value) {
        log.debug("isNullOrEmpty called");

        return value == null || value.isEmpty();
    }

    /**
     * Get a nested field from a root.
     *
     * @param root the root
     * @param field the field
     * @return the nested field
     */
    protected Path<?> getNestedField(Root<?> root, String field) {
        log.debug("getNestedField called");

        String[] fieldParts = field.split("\\.");
        Path<?> path = root.get(fieldParts[0]);

        for (int i = 1; i < fieldParts.length; i++) {
            path = path.get(fieldParts[i]);
        }

        return path;
    }

    /**
     * Build a predicate for a string field.
     *
     * @param criteriaBuilder the criteria builder
     * @param path the path
     * @param value the value
     * @param operator the operator
     * @return the predicate
     * @throws InvalidFilterException if the filter is invalid
     */
    protected Predicate buildStringPredicate(
            CriteriaBuilder criteriaBuilder, Path<?> path, String value, FilterOperator operator) {
        log.debug("buildStringPredicate called");

        Expression<String> expression = criteriaBuilder.lower(path.as(String.class));

        return switch (operator) {
            case EQUALS -> criteriaBuilder.equal(expression, value.toLowerCase());
            case CONTAINS -> criteriaBuilder.like(expression, "%" + value.toLowerCase() + "%");
            case STARTS_WITH -> criteriaBuilder.like(expression, value.toLowerCase() + "%");
            case ENDS_WITH -> criteriaBuilder.like(expression, "%" + value.toLowerCase());
            case NOT_EQUALS -> criteriaBuilder.notEqual(expression, value.toLowerCase());
            default -> throw new InvalidFilterException(
                    messageService.getMessage(ERROR_INVALID_FILTER));
        };
    }

    /**
     * Build a predicate for a date field.
     *
     * @param criteriaBuilder the criteria builder
     * @param path the path
     * @param value the value
     * @param operator the operator
     * @param otherValue the other value
     * @return the predicate
     * @throws InvalidFilterException if the filter is invalid
     * @throws InvalidDateFormatException if the date format is invalid
     */
    protected Predicate buildDatePredicate(
            CriteriaBuilder criteriaBuilder,
            Path<?> path,
            String value,
            FilterOperator operator,
            String otherValue)
            throws InvalidFilterException {
        log.debug("buildDatePredicate called");

        Expression<Instant> expression = path.as(Instant.class);
        Instant parsedValue = parseDate(value);

        switch (operator) {
            case EQUALS:
                return criteriaBuilder.equal(expression, parsedValue);
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(expression, parsedValue);
            case LESS_THAN:
                return criteriaBuilder.lessThan(expression, parsedValue);
            case BETWEEN:
                if (isNullOrEmpty(otherValue)) {
                    throw new InvalidFilterException(
                            messageService.getMessage(ERROR_INVALID_FILTER));
                }

                Instant parsedOtherValue = parseDate(otherValue);

                return criteriaBuilder.between(expression, parsedValue, parsedOtherValue);
            case NOT_EQUALS:
                return criteriaBuilder.notEqual(expression, parsedValue);
            default:
                throw new InvalidFilterException(messageService.getMessage(ERROR_INVALID_FILTER));
        }
    }

    /**
     * Build a predicate for a long field.
     *
     * @param criteriaBuilder the criteria builder
     * @param path the path
     * @param value the value
     * @param operator the operator
     * @param otherValue the other value
     * @return the predicate
     * @throws InvalidFilterException if the filter is invalid
     */
    protected Predicate buildLongPredicate(
            CriteriaBuilder criteriaBuilder,
            Path<?> path,
            String value,
            FilterOperator operator,
            String otherValue)
            throws InvalidFilterException {
        log.debug("buildLongPredicate called");

        Expression<Long> expression = path.as(Long.class);
        Long parsedValue = Long.parseLong(value);

        switch (operator) {
            case EQUALS:
                return criteriaBuilder.equal(expression, parsedValue);
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(expression, parsedValue);
            case LESS_THAN:
                return criteriaBuilder.lessThan(expression, parsedValue);
            case BETWEEN:
                if (isNullOrEmpty(otherValue)) {
                    throw new InvalidFilterException(
                            messageService.getMessage(ERROR_INVALID_FILTER));
                }

                Long parsedOtherValue = Long.parseLong(otherValue);

                return criteriaBuilder.between(expression, parsedValue, parsedOtherValue);
            case NOT_EQUALS:
                return criteriaBuilder.notEqual(expression, parsedValue);
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
    protected Instant parseDate(String value) throws InvalidDateFormatException {
        log.debug("parseDate called");

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
