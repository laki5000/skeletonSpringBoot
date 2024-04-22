package com.example.controller;

import com.example.service.ExampleService;
import com.example.utils.BaseControllerForExceptionHandling;
import com.example.utils.BaseResponse;
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
     * @param loggerService  the logger service
     * @param exampleService the example service
     */
    public ExampleController(LoggerService loggerService, ExampleService exampleService) {
        super(loggerService);
        this.exampleService = exampleService;
    }

    /**
     * Constructor.
     *
     * @param exampleService the example service
     */
    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

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
     * @return an example string
     */
    @GetMapping("/example")
    public ResponseEntity<?> getExample() {
        super.getLoggerService().logInfo(ExampleController.class, "Getting example.");

        String example = exampleService.getExample();

        super.getLoggerService().logInfo(ExampleController.class, "Got example: " + example);

        return ResponseEntity.ok(new BaseResponse(example));
    }

    /**
     * Example method that throws an exception.
     *
     * @return an example string
     */
    @GetMapping("/exception")
    public ResponseEntity<?> makeException() {
        super.getLoggerService().logInfo(ExampleController.class, "Making exception.");

        exampleService.makeException();

        super.getLoggerService().logInfo(ExampleController.class, "This will never be shown.");

        return ResponseEntity.ok("This will never be shown.");
    }
}
