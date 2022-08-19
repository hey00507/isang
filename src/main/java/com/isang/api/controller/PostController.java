package com.isang.api.controller;

import com.isang.api.request.PostCreate;
import com.isang.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @PostMapping("/posts")
    public Map<String,String> post(@RequestBody @Valid PostCreate params) throws Exception {
        postService.write(params);
        return Map.of();
    }

}
