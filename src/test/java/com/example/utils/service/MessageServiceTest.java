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

import static com.example.utils.constants.TestConstants.*;
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

        when(localeResolver.resolveLocale(request)).thenReturn(locale);
        when(requestAttributesMock.getRequest()).thenReturn(request);
        when(localeResolver.resolveLocale(request)).thenReturn(locale);
    }

    @Test
    public void getMessage_ValidKey_ReturnsExpectedMessage() {
        //Arrange
        when(messageSource.getMessage(validKey, null, locale)).thenReturn(expectedMessage);

        //Act
        String message = messageService.getMessage(validKey);

        //Assert
        assertEquals(expectedMessage, message, "Incorrect message returned");

        //Additional verification
        verify(localeResolver, times(1)).resolveLocale(request);
        verify(messageSource, times(1)).getMessage(validKey, null, locale);
    }

    @Test
    public void getMessage_InvalidKey_ReturnsNull() {
        //Arrange
        when(messageSource.getMessage(invalidKey, null, locale)).thenReturn(null);

        //Act
        String message = messageService.getMessage(invalidKey);

        //Assert
        assertNull(message, "Message should be null");

        //Additional verification
        verify(localeResolver, times(1)).resolveLocale(request);
        verify(messageSource, times(1)).getMessage(invalidKey, null, locale);
    }
}