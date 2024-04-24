package com.example.utils.service;

import com.example.exception.MyConflictException;
import com.example.exception.MyNotFoundException;
import com.example.exception.MyNotModifiedException;
import com.example.utils.mapper.BaseMapper;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/** Base service class for CRUD operations. */
@Log4j2
@Getter
public abstract class BaseServiceForCRUD<T, CRQ, URQ, GRP> {
    private final MessageService messageService;
    private final BaseMapper<T, CRQ, URQ, GRP> mapper;
    private final Class<T> clazz;
    private final String tClassName;

    protected abstract JpaRepository<T, Long> getRepository();

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

        String message = validateCreate(entity);

        if (message != null) {
            throw new MyConflictException(messageService.getMessage(message));
        }

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

        Object validationResult = validateUpdate(update);

        if (validationResult instanceof String) {
            throw new MyNotFoundException(messageService.getMessage(validationResult.toString()));
        }

        Object updateMessage = doUpdate(clazz.cast(validationResult), update);

        if (updateMessage instanceof String) {
            throw new MyNotModifiedException(messageService.getMessage(updateMessage.toString()));
        }

        GRP createdResponse = mapper.toGetResponse(clazz.cast(validationResult));

        log.info("{} updated with id: {}", tClassName, id);

        return createdResponse;
    }

    /**
     * Validate an entity.
     *
     * @param entity the entity to validate
     * @return the error message or null if the entity is valid
     */
    protected abstract String validateCreate(CRQ entity);

    /**
     * Validate an entity.
     *
     * @param entity the entity to validate
     * @return If the return value is a String, it indicates an error occurred during validation. If it's a T object, it means everything is okay.
     */
    protected abstract Object validateUpdate(URQ entity);

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
     * @return If the return value is a String, it indicates an error occurred during validation. If it's a null, it means everything is okay.
     */
    protected abstract Object doUpdate(T entity, URQ update);
}
