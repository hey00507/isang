package com.isang.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
    //SSR -> Jsp, Thymeleaf, mustache,freeMaker
    // HTML rendering
    //SPA -> vue, react, vue + SSR = nuxt.js, react + SSR = next.js
    // Javascript  <-> API (JSON)

    @GetMapping("/posts")
    public String get(){
        return "Hello World";
    }
}
