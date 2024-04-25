package com.example.utils.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;
import java.util.Objects;

@Log4j2
@Service
public class MessageService {
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    /**
     * Constructor.
     *
     * @param messageSource  The message source.
     * @param localeResolver The locale resolver.
     */
    public MessageService(MessageSource messageSource, LocaleResolver localeResolver) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }

    /**
     * Get a message for a key.
     *
     * @param key The key.
     * @return The message.
     */
    public String getMessage(String key) {
        log.info("Getting message for key: {}", key);

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        Locale currentLocale = localeResolver.resolveLocale(request);

        String message = messageSource.getMessage(key, null, currentLocale);

        log.info("Got message: {}", message);

        return message;
    }
}
