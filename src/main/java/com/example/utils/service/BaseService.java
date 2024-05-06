package com.example.utils.service;

import com.example.exception.InvalidDateFormatException;
import com.example.utils.mapper.BaseMapper;
import com.example.utils.repository.BaseRepository;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/** Base service class for CRUD operations. */
@Log4j2
@Getter
public abstract class BaseService<T, CreateRequest, UpdateRequest, GetResponse> {
    private final MessageService messageService;
    private final BaseRepository<T, Long> repository;
    private final BaseMapper<T, CreateRequest, UpdateRequest, GetResponse> mapper;
    private final Class<T> entityType;
    private final String entityClassName;

    /**
     * Constructor.
     *
     * @param messageService the message service
     */
    public BaseService(MessageService messageService, BaseRepository<T, Long> repository, BaseMapper<T, CreateRequest, UpdateRequest, GetResponse> mapper,
            Class<T> entityType) {
        this.messageService = messageService;
        this.repository = repository;
        this.mapper = mapper;
        this.entityType = entityType;
        this.entityClassName = entityType.getSimpleName();
    }

    /**
     * Create a new entity.
     *
     * @param createRequest the entity to create
     * @return the created entity
     */
    @Transactional
    public GetResponse create(CreateRequest createRequest) {
        log.info("Creating {}", entityClassName);

        validateCreate(createRequest);

        T createdEntity = getRepository().save(mapper.toEntity(createRequest));
        GetResponse createdGetResponse = mapper.toGetResponse(createdEntity);

        log.info("{} created", entityClassName);

        return createdGetResponse;
    }

    /**
     * Update an entity.
     *
     * @param updateRequest the entity to update
     * @return the updated entity
     */
    @Transactional
    public GetResponse update(UpdateRequest updateRequest) {
        Long id = getIdFromUpdateRequest(updateRequest);

        log.info("Updating {} with id: {}", entityClassName, id);

        T entity = validateUpdate(updateRequest);

        doUpdate(entityType.cast(entity), updateRequest);

        GetResponse updatedGetResponse = mapper.toGetResponse(entityType.cast(entity));

        log.info("{} updated with id: {}", entityClassName, id);

        return updatedGetResponse;
    }

    /**
     * Delete an entity.
     *
     * @param id the id of the entity to delete
     */
    @Transactional
    public void delete(Long id) {
        log.info("Deleting {} with id: {}", entityClassName, id);

        validateDelete(id);

        getRepository().deleteById(id);

        log.info("{} deleted with id: {}", entityClassName, id);
    }

    /**
     * Get entities.
     *
     * @param params the search parameters
     * @return page of entities
     */
    public Page<GetResponse> get(Map<String, String> params) {
        try {
            log.info("Getting {}s", entityClassName);

            Page<T> entities = getRepository().findAllWithCriteria(params);
            Page<GetResponse> pageGetResponse = entities.map(mapper::toGetResponse);

            log.info("Got {}s", entityClassName);

            return pageGetResponse;
        } catch (InvalidDateFormatException e) {
            throw new InvalidDateFormatException(
                    messageService.getMessage("error.invalid.date_format") + " " + e.getMessage());
        }
    }

    /**
     * Validate an entity.
     *
     * @param createRequest the entity to validate
     */
    protected abstract void validateCreate(CreateRequest createRequest);

    /**
     * Validate an entity.
     *
     * @param updateRequest the entity to validate
     * @return the updated entity
     */
    protected abstract T validateUpdate(UpdateRequest updateRequest);

    /**
     * Validate the delete request.
     *
     * @param id the id to delete
     */
    protected abstract void validateDelete(Long id);

    /**
     * Get the id from the update request.
     *
     * @param updateRequest the update request
     * @return the id
     */
    protected abstract Long getIdFromUpdateRequest(UpdateRequest updateRequest);

    /**
     * Update the entity.
     *
     * @param entity the entity to update
     * @param updateRequest the update request
     */
    protected abstract void doUpdate(T entity, UpdateRequest updateRequest);
}
