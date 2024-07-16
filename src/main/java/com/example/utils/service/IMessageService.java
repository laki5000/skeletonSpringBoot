package com.example.utils.service;

/** Service interface for getting messages. */
public interface IMessageService {
    /**
     * Gets a message for a key.
     *
     * @param key the key of the message
     * @return the message
     */
    String getMessage(String key);
}
