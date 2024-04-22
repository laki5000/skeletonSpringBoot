package com.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/** Service to log messages. */
@Service
public class LoggerService {
    private Logger logger;

    /**
     * Logs an info message.
     *
     * @param clazz   the class that calls the method
     * @param message the message to log
     */
    public void logInfo(Class<?> clazz, String message) {
        logger = LogManager.getLogger(clazz);
        logger.info(message);
    }

    /**
     * Logs an error message.
     *
     * @param clazz   the class that calls the method
     * @param message the message to log
     */
    public void logError(Class<?> clazz, String message) {
        logger = LogManager.getLogger(clazz);
        logger.error(message);
    }
}
