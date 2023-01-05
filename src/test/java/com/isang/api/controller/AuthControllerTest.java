package com.isang.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isang.api.domain.Session;
import com.isang.api.domain.User;
import com.isang.api.entity.request.Login;
import com.isang.api.repository.SessionRepository;
import com.isang.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    void clean(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void test() throws Exception{
        //given
        userRepository
                .save(User.builder()
                        .name("이상")
                        .email("hey0507@gmail.com")
                        .password("1234")
                .build());


        Login login = Login.builder()
                .email("hey0507@gmail.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        //expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("로그인 성공 후 세션 한 개 생성")
    void test2() throws Exception{
        //given
        User user = userRepository
                .save(User.builder()
                        .name("이상")
                        .email("hey0507@gmail.com")
                        .password("1234")
                        .build());


        Login login = Login.builder()
                .email("hey0507@gmail.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        //expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andDo(print());

        assertEquals(1L, user.getSessions().size());
        
        
        
    }


    @Test
    @DisplayName("로그인 후 권한이 필요한 페이지에 접속한다 /auth-test")
    void test4() throws Exception{

        User user = User.builder()
                .name("이상")
                .email("hey0507@gmail.com")
                .password("1234")
                .build();
        Session session = user.addSession();
        userRepository.save(user);

//
//        Login login = Login.builder()
//                .email("hey0507@gmail.com")
//                .password("1234")
//                .build();
//
//        String json = objectMapper.writeValueAsString(login);

        //expected
        mockMvc.perform(get("/test-auth")
                        .header("Authorization",session.getAccessToken())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

//        assertEquals(1L, user.getSessions().size());

    }

    @Test
    @DisplayName("로그인 후 검증되지 않는 세션값으로 권한이 필요한 페이지에 접속할 수 없다 /auth-test")
    void test5() throws Exception{

        User user = User.builder()
                .name("이상")
                .email("hey0507@gmail.com")
                .password("1234")
                .build();
        Session session = user.addSession();
        userRepository.save(user);

//
//        Login login = Login.builder()
//                .email("hey0507@gmail.com")
//                .password("1234")
//                .build();
//
//        String json = objectMapper.writeValueAsString(login);

        //expected
        mockMvc.perform(get("/test-auth")
                        .header("Authorization",session.getAccessToken()+"-other")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andDo(print());

//        assertEquals(1L, user.getSessions().size());

    }

}