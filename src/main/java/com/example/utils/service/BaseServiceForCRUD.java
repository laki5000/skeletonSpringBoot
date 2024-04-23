package com.example.utils.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/** Base service class for CRUD operations. */
@Log4j2
public abstract class BaseServiceForCRUD<T> {
    private final MessageService messageService;

    protected abstract JpaRepository<T, Long> getRepository();

    /**
     * Constructor.
     *
     * @param messageService the message service
     */
    public BaseServiceForCRUD(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Create a new entity.
     *
     * @param entity the entity to create
     * @return the created entity
     */
    @Transactional
    public T create(T entity) {
        log.info("Creating {}", entity.getClass().getSimpleName());

        T createdEntity = getRepository().save(entity);

        log.info("{} created", entity.getClass().getSimpleName());

        return createdEntity;
    }
}
