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
public abstract class BaseServiceForCRUD<T, CRQ, URQ, GRP> {
    private final MessageService messageService;
    private final BaseMapper<T, CRQ, URQ, GRP> mapper;
    private final Class<T> clazz;
    private final String tClassName;

    protected abstract BaseRepository<T, Long> getRepository();

    /**
     * Constructor.
     *
     * @param messageService the message service
     */
    public BaseServiceForCRUD(MessageService messageService, BaseMapper<T, CRQ, URQ, GRP> mapper, Class<T> entityType) {
        this.messageService = messageService;
        this.mapper = mapper;
        this.clazz = entityType;
        this.tClassName = entityType.getSimpleName();
    }

    /**
     * Create a new entity.
     *
     * @param entity the entity to create
     * @return the created entity
     */
    @Transactional
    public GRP create(CRQ entity) {
        log.info("Creating {}", tClassName);

        validateCreate(entity);

        T createdEntity = getRepository().save(mapper.toEntity(entity));
        GRP createdResponse = mapper.toGetResponse(createdEntity);

        log.info("{} created", tClassName);

        return createdResponse;
    }

    /**
     * Update an entity.
     *
     * @param update the entity to update
     * @return the updated entity
     */
    @Transactional
    public GRP update(URQ update) {
        Long id = getIdFromUpdateRequest(update);

        log.info("Updating {} with id: {}", tClassName, id);

        T validationResult = validateUpdate(update);

        doUpdate(clazz.cast(validationResult), update);

        GRP createdResponse = mapper.toGetResponse(clazz.cast(validationResult));

        log.info("{} updated with id: {}", tClassName, id);

        return createdResponse;
    }

    /**
     * Delete an entity.
     *
     * @param id the id of the entity to delete
     */
    @Transactional
    public void delete(Long id) {
        log.info("Deleting {} with id: {}", tClassName, id);

        validateDelete(id);

        getRepository().deleteById(id);

        log.info("{} deleted with id: {}", tClassName, id);
    }

    /**
     * Get entities.
     *
     * @param params the search parameters
     * @return page of entities
     */
    public Page<GRP> get(Map<String, String> params) {
        try {
            log.info("Getting {}s", tClassName);

            Page<T> entities = getRepository().findAllWithCriteria(params);
            Page<GRP> responses = entities.map(mapper::toGetResponse);

            log.info("Got {}s", tClassName);

            return responses;
        } catch (InvalidDateFormatException e) {
            throw new InvalidDateFormatException(messageService.getMessage("invalid.date.format") + " " + e.getMessage());
        }
    }

    /**
     * Validate an entity.
     *
     * @param entity the entity to validate
     */
    protected abstract void validateCreate(CRQ entity);

    /**
     * Validate an entity.
     *
     * @param entity the entity to validate
     * @return the updated entity
     */
    protected abstract T validateUpdate(URQ entity);

    /**
     * Validate the delete request.
     *
     * @param id the id to delete
     */
    protected abstract void validateDelete(Long id);

    /**
     * Get the id from the update request.
     *
     * @param entity the update request
     * @return the id
     */
    protected abstract Long getIdFromUpdateRequest(URQ entity);

    /**
     * Update the entity.
     *
     * @param entity the entity to update
     * @param update the update request
     */
    protected abstract void doUpdate(T entity, URQ update);
}
