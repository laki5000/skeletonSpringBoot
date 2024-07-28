package com.example.utils.repository;

import com.example.exception.InvalidDateFormatException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import jakarta.persistence.criteria.Root;
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

    String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    String DATE_TIME_REGEX = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}";
    String DATE_TIME_WITH_MILLIS_REGEX = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}Z";

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter DATE_TIME_WITH_MILLIS_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

    /**
     * Find all entities with criteria.
     *
     * @param params the search parameters
     * @return page of entities
     */
    default Page<T> findAllWithCriteria(Map<String, String> params) {
        int page = getIntParam(params, "page", 0);
        int limit = getIntParam(params, "limit", 10);
        String orderBy = params.getOrDefault("orderBy", "id");
        String orderDirection = params.getOrDefault("orderDirection", "asc");

        params.keySet().removeAll(Arrays.asList("page", "limit", "orderBy", "orderDirection", "password"));

        Pageable pageable = PageRequest.of(page, limit);
        Specification<T> spec = buildSpecification(params, orderBy, orderDirection);

        return findAll(spec, pageable);
    }

    /**
     * Build a specification.
     *
     * @param params the search parameters
     * @param orderBy the field to order by
     * @param orderDirection the order direction
     * @return the specification
     * @throws InvalidDateFormatException if the date format is invalid
     */
    default Specification<T> buildSpecification(Map<String, String> params, String orderBy, String orderDirection) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (isNullOrEmpty(value)) {
                    continue;
                }

                Class<?> javaType = root.get(key).getJavaType();
                if (javaType.equals(String.class)) {
                    predicates.add(criteriaBuilder.like(root.get(key), value + "%"));
                } else if (javaType.equals(Instant.class)) {
                    predicates.add(handleDatePredicate(root, criteriaBuilder, key, value));
                } else {
                    predicates.add(criteriaBuilder.equal(root.get(key), value));
                }
            }

            if (orderBy != null && orderDirection != null) {
                if ("asc".equalsIgnoreCase(orderDirection)) {
                    query.orderBy(criteriaBuilder.asc(root.get(orderBy)));
                } else if ("desc".equalsIgnoreCase(orderDirection)) {
                    query.orderBy(criteriaBuilder.desc(root.get(orderBy)));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Handle date predicate construction.
     *
     * @param root the root type in the from clause
     * @param criteriaBuilder used to construct criteria queries
     * @param key the field name
     * @param value the field value
     * @return a predicate for the date
     * @throws InvalidDateFormatException if the date format is invalid
     */
    private Predicate handleDatePredicate(Root<T> root, CriteriaBuilder criteriaBuilder, String key, String value) throws InvalidDateFormatException {
        if (!value.matches(DATE_REGEX) && !value.matches(DATE_TIME_REGEX) && !value.matches(DATE_TIME_WITH_MILLIS_REGEX)) {
            throw new InvalidDateFormatException(
                    "expected formats: yyyy-MM-dd, yyyy-MM-ddTHH:mm:ss, yyyy-MM-ddTHH:mm:ss.SSSSSSZ, received: " + value);
        }

        String formattedValue = value;
        if (value.matches(DATE_TIME_WITH_MILLIS_REGEX)) {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(value);
            LocalDateTime localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            formattedValue = localDateTime.format(DATE_TIME_WITH_MILLIS_FORMATTER);
        } else if (value.matches(DATE_TIME_REGEX)) {
            LocalDateTime localDateTime = LocalDateTime.parse(value);
            formattedValue = localDateTime.format(DATE_TIME_FORMATTER);
        }

        return criteriaBuilder.like(root.get(key).as(String.class), formattedValue + "%");
    }

    /**
     * Retrieve an integer parameter from the map, with a default value.
     *
     * @param params the parameter map
     * @param key the parameter key
     * @param defaultValue the default value if the key is not found or empty
     * @return the integer value
     */
    private int getIntParam(Map<String, String> params, String key, int defaultValue) {
        return params.get(key) != null && !params.get(key).isEmpty()
                ? Integer.parseInt(params.get(key))
                : defaultValue;
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
}
