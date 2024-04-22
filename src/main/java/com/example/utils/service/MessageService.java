package com.example.utils.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Log4j2
@Service
public class MessageService {
    private final MessageSource messageSource;

    @Value("${spring.mvc.locale}")
    private String languageTag;

    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key) {
        log.info("Getting message for key: {}", key);

        String message = messageSource.getMessage(key, null, Locale.forLanguageTag(languageTag));

        log.info("Got message: {}", message);

        return message;
    }
}
