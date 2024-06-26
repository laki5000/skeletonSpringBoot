package com.example.utils.controller;

import com.example.utils.dto.response.BaseResponse;
import com.example.utils.dto.response.SuccessResponse;
import com.example.utils.service.BaseService;
import com.example.utils.service.MessageService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/** Base controller for CRUD operations. */
@Log4j2
public abstract class BaseController<T, CRQ, URQ, GRP> {
    private final MessageService messageService;

    protected abstract BaseService<T, CRQ, URQ, GRP> getService();

    /**
     * Constructor.
     *
     * @param messageService the message service
     */
    public BaseController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Create a new entity.
     *
     * @param entity the entity to create
     * @return the created entity
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CRQ entity) {
        String className = getService().getTClassName();

        log.info("Creating {}", className);

        GRP createdEntity = getService().create(entity);

        log.info("{} created", className);

        return ResponseEntity.ok(new SuccessResponse(className + " " + messageService.getMessage("success.create"), createdEntity));
    }

    /**
     * Update an entity.
     *
     * @param update the entity to update
     * @return the updated entity
     */
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody URQ update) {
        String className = getService().getTClassName();

        log.info("Updating {}", className);

        GRP updatedEntity = getService().update(update);

        log.info("{} updated", className);

        return ResponseEntity.ok(new SuccessResponse(className + " " + messageService.getMessage("success.update"), updatedEntity));
    }

    /**
     * Delete an entity.
     *
     * @param id the id of the entity to delete
     * @return the response entity
     */
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id) {
        String className = getService().getTClassName();

        log.info("Deleting {}", className);

        getService().delete(id);

        log.info("{} deleted", className);

        return ResponseEntity.ok(new BaseResponse(className + " " + messageService.getMessage("success.delete")));
    }

    /**
     * Get entities.
     *
     * @param params the query parameters
     * @return page of entities
     */
    @GetMapping
    public ResponseEntity<?> get(
                                 @RequestParam(required = false) Map<String, String> params) {
        String className = getService().getTClassName();

        log.info("Getting {}", className);

        Page<GRP> entities = getService().get(params);

        log.info("Got {}", className);

        return ResponseEntity.ok(entities);
    }
}
