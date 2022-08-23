package com.isang.api.controller;

import com.isang.api.domain.Post;
import com.isang.api.request.PostCreate;
import com.isang.api.response.PostResponse;
import com.isang.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public PostResponse get(@PathVariable(name = "postId") Long id){
        return postService.get(id);
    }

    //여러개의 글을 조회하는 API
    @GetMapping("/posts")
    public List<PostResponse> getList(){
        return postService.getList();
    }
}
