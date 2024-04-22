package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Example controller class. */
@RestController
@RequestMapping("${api.base.path}")
public class ExampleController {
    /**
     * Example method.
     *
     * @return An example string.
     */
    @GetMapping("/example")
    public final String getExample() {
        return "example response";
    }
}
