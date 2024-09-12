package com.example.base.controller;

import com.example.utils.dto.request.FilteringDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 * Controller interface for common endpoints.
 *
 * @param <CreateDTO> the create DTO type
 * @param <UpdateDTO> the update DTO type
 */
public interface IBaseController<CreateDTO, UpdateDTO> {
    /**
     * Creates a new entity.
     *
     * @param createDTO the DTO containing the entity's details
     * @return the response entity
     */
    ResponseEntity<?> create(CreateDTO createDTO);

    /**
     * Gets entities.
     *
     * @param page the page number
     * @param limit the number of entities per page
     * @param orderBy the field to order by
     * @param orderDirection the direction to order by
     * @param filteringDTOList the search parameters
     * @return the response entity
     */
    ResponseEntity<?> get(
            int page,
            int limit,
            String orderBy,
            String orderDirection,
            List<FilteringDTO> filteringDTOList);

    /**
     * Updates an existing entity.
     *
     * @param id the id of the entity to update
     * @param updateDTO the DTO containing the entity's details
     * @return the response entity
     */
    ResponseEntity<?> update(Long id, UpdateDTO updateDTO);

    /**
     * Deletes an entity.
     *
     * @param id the id of the entity to delete
     * @return the response entity
     */
    ResponseEntity<?> delete(Long id);
}
