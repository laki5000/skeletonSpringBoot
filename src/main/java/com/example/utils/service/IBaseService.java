package com.example.utils.service;

import com.example.utils.dto.request.FilteringDTO;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * Service Interface for common operations.
 *
 * @param <CreateDTO> the create DTO type
 * @param <UpdateDTO> the update DTO type
 * @param <GetDTO> the get DTO type
 */
public interface IBaseService<CreateDTO, UpdateDTO, GetDTO> {
    /**
     * Creates a new entity.
     *
     * @param createDTO the DTO containing the entity's details
     * @return the created entity
     */
    GetDTO create(CreateDTO createDTO);

    /**
     * Gets entities.
     *
     * @param page the page number
     * @param limit the number of entities per page
     * @param orderBy the field to order by
     * @param orderDirection the direction to order by
     * @param filteringDTOList the search parameters
     * @return the entities
     */
    Page<GetDTO> get(
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
     * @return the updated entity
     */
    GetDTO update(Long id, UpdateDTO updateDTO);

    /**
     * Deletes an entity.
     *
     * @param id the id of the entity to delete
     */
    void delete(Long id);
}
