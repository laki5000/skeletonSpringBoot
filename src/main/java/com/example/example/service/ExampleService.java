package com.example.example.service;

import com.example.utils.service.MessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/** Example service class. */
@Log4j2
@Service
public class ExampleService {
    private final MessageService messageService;

    /**
     * Constructor.
     *
     * @param messageService the message service
     */
    public ExampleService(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Example method.
     *
     * @return An example string.
     */
    public String getExample() {
        log.info("Getting example");

        String example = messageService.getMessage("example.service.message01");

        log.info("Got example: {}", example);

        return example;
    }

    /**
     * Example method that throws an exception.
     */
    public void makeException() {
        log.info("Making exception");

        throw new RuntimeException(messageService.getMessage("example.service.exception01"));
    }
}
