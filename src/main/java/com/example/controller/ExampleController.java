package com.example.controller;

import com.example.service.ExampleService;
import com.example.utils.BaseControllerForExceptionHandling;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Example controller class. */
@RestController
@RequestMapping("${api.base.path}")
public class ExampleController extends BaseControllerForExceptionHandling {
    private final ExampleService exampleService;

    /**
     * Constructor.
     *
     * @param exampleService the example service
     */
    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    /**
     * Example method that returns an example string.
     *
     * @return An example string.
     */
    @GetMapping("/example")
    public ResponseEntity<?> getExample() {
        return ResponseEntity.ok(exampleService.getExample());
    }

    /**
     * Example method that throws an exception.
     *
     * @return An example string.
     */
    @GetMapping("/exception")
    public ResponseEntity<?> makeException() {
        exampleService.makeException();

        return ResponseEntity.ok("This will never be shown.");
    }
}
