package com.example.config;

import com.example.user.mapper.UserMapper;
import com.example.user.mapper.UserMapperImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up mappers.
 *
 */
@Log4j2
@Configuration
public class MapperConfig {
    /**
     * Creates a user mapper bean.
     *
     * @return the user mapper bean
     */
    @Bean
    public UserMapper userMapper() {
        log.info("Creating user mapper bean");

        UserMapper userMapper = new UserMapperImpl();

        log.info("Created user mapper bean");

        return userMapper;
    }
}
