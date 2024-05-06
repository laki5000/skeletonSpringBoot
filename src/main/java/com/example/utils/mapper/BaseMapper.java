package com.example.utils.mapper;

/**
 * Base mapper interface.
 *
 * @param <T>             the entity type
 * @param <CreateRequest> the request type
 * @param <UpdateRequest> the update request type
 */
public interface BaseMapper<T, CreateRequest, UpdateRequest, GetResponse> {
    /**
     * Convert a request to an entity.
     *
     * @param request the request
     * @return the entity
     */
    public T toEntity(CreateRequest request);

    /**
     * Convert an entity to a response.
     *
     * @param entity the entity
     * @return the response
     */
    public GetResponse toGetResponse(T entity);
}
