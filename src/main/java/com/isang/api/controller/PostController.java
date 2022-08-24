package com.isang.api.controller;

import com.isang.api.request.PostCreate;
import com.isang.api.response.PostResponse;
import com.isang.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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


    /**
     * 페이징이 필요한 이유?
     *
     * 글이 너무 많은 경우? -> 비용이 많이 들게 된다.
     * 글이 -> 100,000,000 -> DB 글을 모두 조회할 경우? -> DB 가 뻗는다.
     * DB -> 애플리케이션 서버로 전달하는 시간 , 트래픽 비용이 많이 발생할 수 있다.
     * -> 게시글의 경우는 DB 에서 모두 긁어올 일이 없음 -> 요청한 페이지에 대해서만 요청을 하도록 함
     */
    @GetMapping("/posts")
    public List<PostResponse> getList(Pageable pageable){
        return postService.getList(pageable);
    }
}
