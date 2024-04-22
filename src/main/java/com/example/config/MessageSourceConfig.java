package com.example.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Configuration class for setting up message sources and locale resolution.
 */
@Log4j2
@Configuration
public class MessageSourceConfig implements WebMvcConfigurer {
    @Value("${spring.mvc.locale}")
    private String languageTag;

    /**
     * Message source bean for loading messages from properties files.
     *
     * @return MessageSource bean
     */
    @Bean
    public MessageSource messageSource() {
        log.info("Creating message source bean");

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");

        log.info("Message source bean created");

        return messageSource;
    }

    /**
     * Locale resolver bean for resolving locale from session.
     *
     * @return LocaleResolver bean
     */
    @Bean
    public LocaleResolver localeResolver() {
        log.info("Creating locale resolver bean");

        SessionLocaleResolver resolver = new SessionLocaleResolver();

        resolver.setDefaultLocale(Locale.forLanguageTag(languageTag));

        log.info("Locale resolver bean created");

        return resolver;
    }

    /**
     * Add locale change interceptor to the registry.
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("Adding locale change interceptor to the registry");

        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();

        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);

        log.info("Locale change interceptor added to the registry");
    }
}
