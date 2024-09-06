package com.example.utils.service;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

/** Service class for getting messages. */
@Log4j2
@RequiredArgsConstructor
@Service
@SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "False positive - no mutable fields exposed")
public class MessageService implements IMessageService {
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    /**
     * Gets a message for a key.
     *
     * @param key the key of the message
     * @return the message
     */
    public String getMessage(String key) {
        log.debug("Getting message for key: {}", key);

        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {

            return null;
        }

        HttpServletRequest request = attributes.getRequest();

        Locale currentLocale = localeResolver.resolveLocale(request);

        return messageSource.getMessage(key, null, currentLocale);
    }
}
