package com.example.service;

import com.example.utils.MessageService;
import org.springframework.stereotype.Service;

/** Example service class. */
@Service
public class ExampleService {
    private final LoggerService loggerService;
    private final MessageService messageService;

    /**
     * Constructor.
     *
     * @param messageService the message service
     * @param loggerService  the logger service
     */
    public ExampleService(MessageService messageService, LoggerService loggerService) {
        this.messageService = messageService;
        this.loggerService = loggerService;
    }

    /**
     * Example method.
     *
     * @return An example string.
     */
    public String getExample() {
        loggerService.logInfo(ExampleService.class, "Example log message.");

        return messageService.getMessage("example.message");
    }

    /**
     * Example method that throws an exception.
     */
    public void makeException() {
        loggerService.logInfo(ExampleService.class, "Example error throwing.");

        throw new RuntimeException("This is an example exception");
    }
}
