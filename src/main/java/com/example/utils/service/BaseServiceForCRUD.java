package com.example.utils.service;

import com.example.exception.MyConflictException;
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
     * Validate an entity.
     *
     * @param entity the entity to validate
     * @return the error message or null if the entity is valid
     */
    protected abstract String validateCreate(CRQ entity);
}
