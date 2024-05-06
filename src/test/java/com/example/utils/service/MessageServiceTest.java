package com.example.utils.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;

import static com.example.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
    @InjectMocks
    private MessageService messageService;
    @Mock
    private MessageSource messageSource;
    @Mock
    private LocaleResolver localeResolver;
    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setup() {
        ServletRequestAttributes requestAttributesMock = mock(ServletRequestAttributes.class);
        RequestContextHolder.setRequestAttributes(requestAttributesMock);

        when(localeResolver.resolveLocale(request)).thenReturn(LOCALE);
        when(requestAttributesMock.getRequest()).thenReturn(request);
        when(localeResolver.resolveLocale(request)).thenReturn(LOCALE);
    }

    @Test
    public void getMessage_ValidKey_ReturnsExpectedMessage() {
        // Given
        when(messageSource.getMessage(VALID_KEY, null, LOCALE)).thenReturn(EXPECTED_MESSAGE);

        // When
        String message = messageService.getMessage(VALID_KEY);

        // Then
        assertEquals(EXPECTED_MESSAGE, message, "Incorrect message returned");

        verify(localeResolver, times(1)).resolveLocale(request);
        verify(messageSource, times(1)).getMessage(VALID_KEY, null, LOCALE);
    }

    @Test
    public void getMessage_InvalidKey_ReturnsNull() {
        // Given
        when(messageSource.getMessage(INVALID_KEY, null, LOCALE)).thenReturn(null);

        // When
        String message = messageService.getMessage(INVALID_KEY);

        // Then
        assertNull(message, "Message should be null");

        verify(localeResolver, times(1)).resolveLocale(request);
        verify(messageSource, times(1)).getMessage(INVALID_KEY, null, LOCALE);
    }
}
