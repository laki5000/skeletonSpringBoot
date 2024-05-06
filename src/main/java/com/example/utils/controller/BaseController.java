package com.example.utils.controller;

import com.example.utils.dto.response.BaseResponseDTO;
import com.example.utils.dto.response.SuccessResponseDTO;
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
public abstract class BaseController<T, CreateRequest, UpdateRequest, GetResponse> {
    private final MessageService messageService;

    protected abstract BaseService<T, CreateRequest, UpdateRequest, GetResponse> getService();

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
     * @param createRequest the entity to create
     * @return the created entity
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateRequest createRequest) {
        String className = getService().getEntityClassName();

        log.info("Creating {}", className);

        GetResponse createdGetResponse = getService().create(createRequest);

        log.info("{} created", className);

        return ResponseEntity.ok(new SuccessResponseDTO(className + " " + messageService.getMessage("success.create"), createdGetResponse));
    }

    /**
     * Update an entity.
     *
     * @param updateRequest the entity to update
     * @return the updated entity
     */
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody UpdateRequest updateRequest) {
        String className = getService().getEntityClassName();

        log.info("Updating {}", className);

        GetResponse updatedGetResponse = getService().update(updateRequest);

        log.info("{} updated", className);

        return ResponseEntity.ok(new SuccessResponseDTO(className + " " + messageService.getMessage("success.update"), updatedGetResponse));
    }

    /**
     * Delete an entity.
     *
     * @param id the id of the entity to delete
     * @return the response entity
     */
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id) {
        String className = getService().getEntityClassName();

        log.info("Deleting {}", className);

        getService().delete(id);

        log.info("{} deleted", className);

        return ResponseEntity.ok(new BaseResponseDTO(className + " " + messageService.getMessage("success.delete")));
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
        String className = getService().getEntityClassName();

        log.info("Getting {}", className);

        Page<GetResponse> pageGetResponse = getService().get(params);

        log.info("Got {}", className);

        return ResponseEntity.ok(pageGetResponse);
    }


}
