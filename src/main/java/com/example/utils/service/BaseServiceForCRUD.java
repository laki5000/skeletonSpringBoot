package com.example.utils.service;

import com.example.exception.MyConflictException;
import com.example.utils.mapper.BaseMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/** Base service class for CRUD operations. */
@Log4j2
public abstract class BaseServiceForCRUD<T, CRQ, URQ, GRP> {
    private final MessageService messageService;
    private final BaseMapper<T, CRQ, URQ, GRP> mapper;

    protected abstract JpaRepository<T, Long> getRepository();

    /**
     * Constructor.
     *
     * @param messageService the message service
     */
    public BaseServiceForCRUD(MessageService messageService, BaseMapper<T, CRQ, URQ, GRP> mapper) {
        this.messageService = messageService;
        this.mapper = mapper;
    }

    /**
     * Create a new entity.
     *
     * @param entity the entity to create
     * @return the created entity
     */
    @Transactional
    public GRP create(CRQ entity) {
        log.info("Creating {}", entity.getClass().getSimpleName());

        String message = validateCreate(entity);

        if (message != null) {
            throw new MyConflictException(messageService.getMessage(message));
        }

        T createdEntity = getRepository().save(mapper.toEntity(entity));
        GRP createdResponse = mapper.toGetResponse(createdEntity);

        log.info("{} created", entity.getClass().getSimpleName());

        return createdResponse;
    }

    /**
     * Validate an entity.
     *
     * @param entity the entity to validate
     * @return the error message or null if the entity is valid
     */
    protected abstract String validateCreate(CRQ entity);
}
