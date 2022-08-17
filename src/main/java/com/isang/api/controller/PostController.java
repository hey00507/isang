package com.isang.api.controller;

import com.isang.api.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
public class PostController {
    //SSR -> Jsp, Thymeleaf, mustache,freeMaker
    // HTML rendering
    //SPA -> vue, react, vue + SSR = nuxt.js, react + SSR = next.js
    // Javascript  <-> API (JSON)

    //Http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 글등록
    // POST Method
    // @RequestParam -> String 으로 직접 받기, Map 을 이용하여 받기, 데이터 형태에 맞는 클래스를 생성한다 (추천)
    // @RequestParam 등의 Key Value 형태의 값 받기에는 도메인 데이터를 모두 담기에는 힘들다는 한계가 있다. (ex. 글 쓴 user 의 값 등)

    /**
     * {
     *     "title: "xxx",
     *     "content" : "xxxx",
     *     "user" :{
     *         "id" : "tt",
     *         "name" : "ttt"
     *     }
     * }
     */
    // 온전한 모든 데이터의 형태로 넘길 수 있으므로 json 을 권장함
    @PostMapping("/posts")
    public String post(@RequestBody PostCreate params){
        log.info("Post Json 파라미터 : {}", params.toString());
        return "Hello World";
    }

}
