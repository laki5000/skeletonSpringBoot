package com.example.utils.service;

/**
 * Service interface for getting messages.
 */
public interface IMessageService {
    /**
     * Get a message for a key.
     *
     * @param key The key.
     * @return The message.
     */
    String getMessage(String key);
}
