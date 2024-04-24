package com.example.utils.mapper;

/**
 * Base mapper interface.
 *
 * @param <T>   the entity type
 * @param <CRQ> the request type
 * @param <URQ> the update request type
 */
public interface BaseMapper<T, CRQ, URQ, GRP> {
    /**
     * Convert a request to an entity.
     *
     * @param request the request
     * @return the entity
     */
    public T toEntity(CRQ request);

    /**
     * Convert an entity to a response.
     *
     * @param entity the entity
     * @return the response
     */
    public GRP toGetResponse(T entity);
}
