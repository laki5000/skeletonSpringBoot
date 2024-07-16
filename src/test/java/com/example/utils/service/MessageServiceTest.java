package com.example.utils.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.LocaleResolver;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
  @InjectMocks private IMessageService messageService;
  @Mock private MessageSource messageSource;
  @Mock private LocaleResolver localeResolver;

  @Test
  public void getMessage_Success() {

  }

  @Test
  public void getMessage_InvalidKey() {

  }
}
