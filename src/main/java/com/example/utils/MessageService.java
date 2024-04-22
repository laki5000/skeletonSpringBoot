package com.example.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageService {
    private final LoggerService loggerService;
    private final MessageSource messageSource;

    @Value("${spring.mvc.locale}")
    private String languageTag;

    public MessageService(MessageSource messageSource, LoggerService loggerService) {
        this.messageSource = messageSource;
        this.loggerService = loggerService;
    }

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, Locale.forLanguageTag(languageTag));
    }
}
