package com.example.service;

import com.example.utils.LoggerService;
import org.springframework.stereotype.Service;

/** Example service class. */
@Service
public class ExampleService {
    private final LoggerService loggerService;

    /**
     * Constructor.
     *
     * @param loggerService the logger service
     */
    public ExampleService(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    /**
     * Example method.
     *
     * @return An example string.
     */
    public String getExample() {
        loggerService.logInfo(ExampleService.class, "Example log message.");

        return "Example response.";
    }

    /**
     * Example method that throws an exception.
     */
    public void makeException() {
        loggerService.logInfo(ExampleService.class, "Example error throwing.");

        throw new RuntimeException("This is an example exception");
    }
}
