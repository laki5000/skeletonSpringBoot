package com.example.example.controller;

import com.example.example.service.ExampleService;
import com.example.utils.controller.GlobalExceptionHandler;
import com.example.utils.dto.response.BaseResponse;
import com.example.utils.service.MessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Example controller class. */
@Log4j2
@RestController
@RequestMapping("${api.base.path}")
public class ExampleController extends GlobalExceptionHandler {
    private final MessageService messageService;
    private final ExampleService exampleService;

    /**
     * Constructor.
     *
     * @param messageService the message service
     * @param exampleService the example service
     */
    public ExampleController(MessageService messageService, ExampleService exampleService) {
        super(messageService);
        this.messageService = messageService;
        this.exampleService = exampleService;
    }

    /**
     * Example method that returns an example string.
     *
     * @return an example string
     */
    @GetMapping("/example")
    public ResponseEntity<?> getExample() {
        log.info("Getting example");

        String example = exampleService.getExample();

        log.info("Got example: {}", example);

        return ResponseEntity.ok(new BaseResponse(example));
    }

    /**
     * Example method that throws an exception.
     *
     * @return an example string
     */
    @GetMapping("/exception")
    public ResponseEntity<?> makeException() {
        log.info("Making exception");

        exampleService.makeException();

        log.info("Exception made");

        return ResponseEntity.ok(new BaseResponse(messageService.getMessage("example.controller.response01")));
    }
}
