package com.isang.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc; //application/json (요즘 트렌드) , 이전엔 application/x-www-form-urlencoded

    @Test
    @DisplayName("/posts 요청시 Hello World를 출력한다.")
    void test() throws Exception {
        // 글 제목

        // 글 내용



        //expected

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\" : \"제목입니당당당\" , \"content\" : \"내용이구요 우하하 \"}")
                )
                .andExpect(status().isOk()) // MockMvcResultMatchers -> 테스트 코드의 결과가 일치하는지
                .andExpect(content().string("Hello World"))
                .andDo(print());// MockMvcResultHandlers.print() -> 테스트 메서드의 진행상황 요약
    }

    @Test
    @DisplayName("/posts 요청시 title 값은 필수다.")
    void test2() throws Exception {

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\" : \"\" , \"content\" : \"내용이구요 우하하 \"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("제목이 비어있으면 안된당께요."))
                //.andExpect(content().string("Hello World"))
                .andDo(print());
    }
}