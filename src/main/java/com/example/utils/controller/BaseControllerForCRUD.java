package com.example.utils.controller;

import com.example.utils.dto.response.SuccessResponse;
import com.example.utils.service.BaseServiceForCRUD;
import com.example.utils.service.MessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/** Base controller for CRUD operations. */
@Log4j2
public abstract class BaseControllerForCRUD<T, CRQ, URQ, GRP> extends BaseControllerForExceptionHandling {
    protected abstract BaseServiceForCRUD<T, CRQ, URQ, GRP> getService();

    private final String tClassName;

    /**
     * Constructor.
     *
     * @param messageService the message service
     */
    public BaseControllerForCRUD(MessageService messageService, Class<T> entityType) {
        super(messageService);
        this.tClassName = entityType.getSimpleName();
    }

    /**
     * Create a new entity.
     *
     * @param entity the entity to create
     * @return the created entity
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CRQ entity) {
        String className = tClassName + " ";

        log.info("Creating {}", className);

        GRP createdEntity = getService().create(entity);

        log.info("{} created", className);

        return ResponseEntity
                .ok(new SuccessResponse(className + getMessageService().getMessage("create.success"), createdEntity));
    }
}
