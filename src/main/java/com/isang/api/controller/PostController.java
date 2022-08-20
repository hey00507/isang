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
        // Post 요청의 경우, 200 이나 201 을 많이 사용함
        //Case1. 저장한 데이터 Entity -> response 로 응답하기
        //Case2. 저장한 데이터 Entity 의 PK 만 응답하는 경우 (primary_id)
        //Client 에서는 수신한 id 를 post 조회 API 를 통해서 글 데이터를 수신 받음
        //Case3. 응답이 필요 없는 경우 -> Client 에서 모든 POST 데이터 Context 를 잘 관리하는 경우


        // Bad Case : 서버 단에서 반드시 이렇게 될 거다라고 fix 하는 것은 좋지 않다.
        // 서버 쪽에서는 차라리 유연하게 대응하는 것이 좋음 -> 코드를 잘 짜자.
        postService.write(params);
    }
}
