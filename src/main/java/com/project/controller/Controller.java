package com.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.base.path}")
public class Controller {
    @GetMapping("/endpoint")
    public final String getEndpoint() {
        return "GET request handled at: ${api.basePath}/endpoint";
    }

    public void nullPointerExceptionExample() {
        String str = null;
        if (str.equals("example")) { // NullPointerException-ra fog hibát dobni
            System.out.println("Do something");
        }
    }
}
