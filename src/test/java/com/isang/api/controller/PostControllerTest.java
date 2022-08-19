package com.isang.api.controller;

import com.isang.api.domain.Post;
import com.isang.api.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc; //application/json (요즘 트렌드) , 이전엔 application/x-www-form-urlencoded

    @Autowired
    PostRepository postRepository;

    // 각각의 메서드들이 실행되기 전에 실행하는 메서드
    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }


    @Test
    @DisplayName("/posts 요청시 비어있는 맵을 리턴한다.")
    void test() throws Exception {
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\" : \"제목입니당당당\" , \"content\" : \"내용이구요 우하하 \"}")
                )
                .andExpect(status().isOk()) // MockMvcResultMatchers -> 테스트 코드의 결과가 일치하는지
                .andExpect(content().string("{}"))
                .andDo(print());// MockMvcResultHandlers.print() -> 테스트 메서드의 진행상황 요약

        // DB 에 post 한개가 등록됨 -> 3번째 테스트에 오류가 나게됨
    }
    @Test
    @DisplayName("/posts 요청시 title 값은 필수다.")
    void test2() throws Exception {

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\" : \"\" , \"content\" : \"내용이구요 우하하 \"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("제목이 비어있으면 안된당께요."))
                .andDo(print());
    }
    @Test
    @DisplayName("/posts 요청시 DB 에 값이 저장된다.")
    void test3() throws Exception {


        // when
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\" : \"제목이란다.\" , \"content\" : \"내용이구요 우하하 \"}")
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1L , postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목이란다.", post.getTitle());
        assertEquals("내용이구요 우하하 ", post.getContent());
        //db -> post 2개 등록되는 부분은 개선이 필요 -> 각각의 테스트들은 모두 실행할 때도, 독립적으로 실행할 때도 무결성이 유지되어야함
    }
}