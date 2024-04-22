package com.example.service;

import org.springframework.stereotype.Service;

/** Example service class. */
@Service
public class ExampleService {
    /**
     * Example method.
     *
     * @return An example string.
     */
    public String getExample() {
        throw new RuntimeException("This is an example exception");
    }
}
