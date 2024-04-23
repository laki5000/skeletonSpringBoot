package com.example.config;

import com.example.user.mapper.UserMapper;
import com.example.user.mapper.UserMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public UserMapper userMapper() {
        return new UserMapperImpl();
    }
}
