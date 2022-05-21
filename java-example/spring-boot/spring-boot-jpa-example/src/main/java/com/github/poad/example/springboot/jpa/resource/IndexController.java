package com.github.poad.example.springboot.jpa.resource;

import org.springframework.data.annotation.Immutable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Immutable
@Controller
@Validated
@RequestMapping("/")
public class IndexController {
    @GetMapping
    public String get() {
        return "Hello World!";
    }
}
