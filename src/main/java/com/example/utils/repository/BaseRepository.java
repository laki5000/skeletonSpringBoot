package com.example.utils.repository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Base repository interface for CRUD operations.
 *
 * @param <T>  the entity type
 * @param <ID> the ID type
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    /**
     * Find all entities with criteria.
     *
     * @param params         the search parameters
     * @return page of entities
     */
    default Page<T> findAllWithCriteria(
            Map<String, String> params) {
        int page = params.get("page") != null && !params.get("page").isEmpty() ? Integer.parseInt(params.get("page")) : 0;
        int limit = params.get("limit") != null && !params.get("page").isEmpty() ? Integer.parseInt(params.get("limit")) : 10;
        String orderBy = params.get("orderBy") != null && !params.get("orderBy").isEmpty() ? params.get("orderBy") : "id";
        String orderDirection = params.get("orderDirection") != null && !params.get("orderDirection").isEmpty() ? params.get("orderDirection") : "asc";

        params.remove("page");
        params.remove("limit");
        params.remove("orderBy");
        params.remove("orderDirection");

        Pageable pageable = PageRequest.of(page, limit);
        Specification<T> spec = buildSpecification(params, orderBy, orderDirection);

        return findAll(spec, pageable);
    }

    /**
     * Build a specification.
     *
     * @param params         the search parameters
     * @param orderBy        the field to order by
     * @param orderDirection the order direction
     * @return the specification
     */
    default Specification<T> buildSpecification(Map<String, String> params, String orderBy, String orderDirection) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (value == null || value.isEmpty()) {
                    continue;
                }

                if (root.get(key).getJavaType() == String.class) {
                    predicates.add(criteriaBuilder.like(root.get(key), value + "%"));
                } else {
                    predicates.add(criteriaBuilder.equal(root.get(key), value));
                }
            }

            if (Objects.nonNull(orderBy) && Objects.nonNull(orderDirection)) {
                if ("asc".equalsIgnoreCase(orderDirection)) {
                    query.orderBy(criteriaBuilder.asc(root.get(orderBy)));
                } else if ("desc".equalsIgnoreCase(orderDirection)) {
                    query.orderBy(criteriaBuilder.desc(root.get(orderBy)));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
