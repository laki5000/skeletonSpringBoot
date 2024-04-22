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
        return "Example response.";
    }

    /**
     * Example method that throws an exception.
     */
    public void makeException() {
        throw new RuntimeException("This is an example exception");
    }
}
