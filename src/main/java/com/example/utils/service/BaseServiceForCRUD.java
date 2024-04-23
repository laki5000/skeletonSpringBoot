package com.example.utils.service;

import com.example.exception.MyConflictException;
import com.example.utils.mapper.BaseMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/** Base service class for CRUD operations. */
@Log4j2
public abstract class BaseServiceForCRUD<T, RQ> {
    private final MessageService messageService;
    private final BaseMapper<T, RQ> mapper;

    protected abstract JpaRepository<T, Long> getRepository();

    /**
     * Constructor.
     *
     * @param messageService the message service
     */
    public BaseServiceForCRUD(MessageService messageService, BaseMapper<T, RQ> mapper) {
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
    public T create(RQ entity) {
        log.info("Creating {}", entity.getClass().getSimpleName());

        String message = validate(entity);

        if (message != null) {
            throw new MyConflictException(messageService.getMessage(message));
        }

        T createdEntity = getRepository().save(mapper.toEntity(entity));

        log.info("{} created", entity.getClass().getSimpleName());

        return createdEntity;
    }

    protected abstract String validate(RQ entity);
}
