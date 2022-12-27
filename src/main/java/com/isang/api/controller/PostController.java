package com.isang.api.controller;

import com.isang.api.entity.request.PostCreate;
import com.isang.api.entity.request.PostEdit;
import com.isang.api.entity.request.PostSearch;
import com.isang.api.entity.response.PostResponse;
import com.isang.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post")
    public void post(@RequestBody @Valid PostCreate request) {
        request.validate();
        postService.write(request);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/post/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long id){
        return postService.get(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/post")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch){
        return postService.getList(postSearch);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/post/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request){
        postService.edit(postId,request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/post/{postId}")
    public void delete(@PathVariable Long postId){
        postService.delete(postId);
    }
}
