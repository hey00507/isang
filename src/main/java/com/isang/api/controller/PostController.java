package com.isang.api.controller;

import com.isang.api.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
public class PostController {
    @PostMapping("/posts")
    public Map<String,String> post(@RequestBody @Valid PostCreate params) throws Exception {

        /*
        바인딩 리절트를 통한 에러 반환의 경우?
        1. 매번 메서드마다 값을 검증해야한다.
            > 개발자가 까먹을 가능성
            > 검증 부분에서 버그가 발생할 여지
            > 지겨움 (간지가 안남)
        2. 응답값에 HashMap -> 응답 클래스를 생성함
        3. 여러개의 에러처리가 힘듦
        4. 세번이상의 반복작업을 피해야함 -> 자동화가 필요
        - ControllerAdvice 이용
        */
        log.info("Post Json 파라미터 : {}", params);
        return Map.of();
    }

}
