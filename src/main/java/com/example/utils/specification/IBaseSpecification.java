package com.example.utils.specification;

import com.example.utils.dto.request.FilteringDTO;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specification interface for base specification.
 *
 * @param <T> the type of the entity
 */
public interface IBaseSpecification<T> {
    /**
     * Build a specification.
     *
     * @param filteringDTOList the search parameters
     * @param orderBy the field to order by
     * @param orderDirection the direction to order by
     * @return the specification
     */
    Specification<T> buildSpecification(
            List<FilteringDTO> filteringDTOList, String orderBy, String orderDirection);

    /**
     * Remove a parameter from the list.
     *
     * @param filteringDTOList the search parameters
     * @param key the parameter key
     */
    void removeParam(List<FilteringDTO> filteringDTOList, String key);
}
