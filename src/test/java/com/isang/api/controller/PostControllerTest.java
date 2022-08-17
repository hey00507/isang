package com.isang.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/posts 요청시 Hello World를 출력한다.")
    void test() throws Exception {
        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
                .andExpect(status().isOk()) // MockMvcResultMatchers -> 테스트 코드의 결과가 일치하는지
                .andExpect(content().string("Hello World"))
                .andDo(print());// MockMvcResultHandlers.print() -> 테스트 메서드의 진행상황 요약
    }

}