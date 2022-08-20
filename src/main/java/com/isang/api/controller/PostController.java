package com.isang.api.controller;

import com.isang.api.domain.Post;
import com.isang.api.request.PostCreate;
import com.isang.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate params) {
        postService.write(params);
    }


    @GetMapping("/posts/{postId}")
    public Post get(@PathVariable(name = "postId") Long id){
        return postService.get(id);
    }
}
