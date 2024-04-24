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
     * @param page           the page number
     * @param limit          the page size
     * @param id             the ID
     * @param orderBy        the order by field
     * @param orderDirection the order direction
     * @return page of entities
     */
    default Page<T> findAllWithCriteria(
            int page,
            int limit,
            Long id,
            String orderBy,
            String orderDirection) {
        Pageable pageable = PageRequest.of(page, limit);
        Specification<T> spec = buildSpecification(id, orderBy, orderDirection);

        return findAll(spec, pageable);
    }

    /**
     * Build a specification.
     *
     * @param id             the ID
     * @param orderBy        the order by field
     * @param orderDirection the order direction
     * @return the specification
     */
    default Specification<T> buildSpecification(Long id, String orderBy, String orderDirection) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(id)) {
                predicates.add(criteriaBuilder.equal(root.get("id"), id));
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
