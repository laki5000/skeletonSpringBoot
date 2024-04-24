package com.example.utils.mapper;

/**
 * Base mapper interface.
 *
 * @param <T>  the entity type
 * @param <RQ> the request type
 */
public interface BaseMapper<T, RQ> {
    /**
     * Convert a request to an entity.
     *
     * @param request the request
     * @return the entity
     */
    public T toEntity(RQ request);
}
