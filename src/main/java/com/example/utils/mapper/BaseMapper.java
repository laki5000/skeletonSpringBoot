package com.example.utils.mapper;

/**
 * Base mapper interface.
 *
 * @param <T>             the entity type
 * @param <UpdateRequest> the update request type
 * @param <CreateRequest> the request type
 */
public interface BaseMapper<T, CreateRequest, UpdateRequest, GetResponse> {
    /**
     * Convert a request to an entity.
     *
     * @param createRequestDTO the request
     * @return the entity
     */
    public T toEntity(CreateRequest createRequestDTO);

    /**
     * Convert an entity to a response.
     *
     * @param t the entity
     * @return the response
     */
    public GetResponse toGetResponse(T t);
}
