package com.example.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * Configuration class for setting up message sources and locale resolution.
 */
@Log4j2
@Configuration
public class MessageSourceConfig implements WebMvcConfigurer {
    /**
     * Creates a message source bean.
     *
     * @return the message source bean
     */
    @Bean
    public MessageSource messageSource() {
        log.info("Creating message source bean");

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");

        log.info("Created message source bean");

        return messageSource;
    }

    /**
     * Creates a locale resolver bean.
     *
     * @return the locale resolver bean
     */
    @Bean
    public LocaleResolver localeResolver() {
        log.info("Creating locale resolver bean");

        CookieLocaleResolver localeResolver = new CookieLocaleResolver();

        localeResolver.setDefaultLocale(Locale.getDefault());
        localeResolver.setCookieName("lang");

        log.info("Created locale resolver bean");

        return localeResolver;
    }

    /**
     * Adds a locale change interceptor to the registry.
     *
     * @param registry the interceptor registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("Adding locale change interceptor to registry");

        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();

        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);

        log.info("Added locale change interceptor to registry");
    }
}
