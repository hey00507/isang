package com.isang.api.controller;

import com.isang.api.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String,String> post(@RequestBody @Valid PostCreate params, BindingResult result) throws Exception {
        // 데이터를 검증하는 이유
        // 1. client 측에서 생기는 human error , 실수로 값을 보내지 않는 경우
        // 2. client bug 로 값이 누락되는 경우
        // 3. 외부의 공격을 통해 값이 조작되는 경우
        // 4. DB 에 값을 저장할 때 의도치 않은 오류가 발 생할 수 있음
        // 5. 서버단의 에러를 막기 위함


        String title = params.getTitle();

        if(result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError firstFieldError = fieldErrors.get(0);
            String fieldName = firstFieldError.getField();
            String errorMessage = firstFieldError.getDefaultMessage();
            Map<String,String> error = new HashMap<>();
            error.put(fieldName,errorMessage);
            return error;
        }
//        if(title == null || title.equals("")){
//            throw new Exception("타이틀 값이 없음!");
        /**
         * title 이 "", "    ", "수천만글자" 일경우?
         */

//        }

        // 개발이 빡세다 -> 3번이상 반복될 경우엔 뭔가 잘못된 건 아닐지 의심할 것
        // 누락될 가능성이 있음 -> 검증해야할게 많다. (꼼꼼하지 않을 가능성이 큼)
        // 개발자스럽지 않음 -> 간지가 안남

        String content = params.getContent();

        log.info("Post Json 파라미터 : {}", params);
        // {"title" : "타이틀 값이 없습니다."}
        return Map.of();
    }

}
