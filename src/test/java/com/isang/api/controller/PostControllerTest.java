package com.isang.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isang.api.domain.Post;
import com.isang.api.repository.PostRepository;
import com.isang.api.entity.request.PostCreate;
import com.isang.api.entity.request.PostEdit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    ObjectMapper objectMapper;
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
    void returnedObjectTest() throws Exception {



        // given
        //PostCreate request = new PostCreate("제목입니다.", "내용입니다.");
        // 생성자를 통한 주입의 경우엔 추후에 비롯되는 에러를 파악하기 힘들어짐 (생성자에서의 파라미터 순서를 변경하는 경우) -> 빌더를 써야하는 이유

        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        // context 에 담아줄 수 있게끔 ObjectMapper 를 json string 형태로 변경해줌 (ObjectMapper 는 매우 자주 쓰이므로 꼭 기억)

        String json = objectMapper.writeValueAsString(request);// 추후에 테스트 케이스를 수정하기 용이해짐

        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("/posts 요청시 title 값은 필수다.")
    void titleValidationTest() throws Exception {


        //given
        PostCreate request = PostCreate.builder()
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("제목이 비어있으면 안된당께요."))
                .andDo(print());
    }
    @Test
    @DisplayName("/posts 요청시 DB 에 값이 저장된다.")
    void dbInsertTest() throws Exception {


        //given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1L , postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
        //db -> post 2개 등록되는 부분은 개선이 필요 -> 각각의 테스트들은 모두 실행할 때도, 독립적으로 실행할 때도 무결성이 유지되어야함
    }


    @Test
    @DisplayName("글 한 개 조회")
    void getPost() throws Exception {
        //given

        Post post = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(post);

        //when
        mockMvc.perform(get("/posts/{postId}", post.getId())
                    .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.content").value(post.getContent()))
                .andDo(print());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void getPostList() throws Exception {
        //given
        List<Post> requestPosts = (
                IntStream.range(1,31)
                        .mapToObj(i -> Post.builder()
                                .title("이상 제목 " + i)
                                .content("반포자이 " + i)
                                .build())
                        .collect(Collectors.toList())
        ); // 인트스트림을 통한 반복문
        postRepository.saveAll(requestPosts);

        //when
        mockMvc.perform(get("/posts?page=1&size=5")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()",is(5)))
                .andExpect(jsonPath("$[0].id").value(31))
                .andExpect(jsonPath("$[0].title").value("이상 제목 30"))
                .andExpect(jsonPath("$[0].content").value("반포자이 30"))
                .andDo(print());
    }


    @Test
    @DisplayName("페이지를 0으로 요청해도 1의 페이지 값을 가지고 온다.")
    void getPostListWithPage0() throws Exception {
        //given
        List<Post> requestPosts = (
                IntStream.range(1,31)
                        .mapToObj(i -> Post.builder()
                                .title("이상 제목 " + i)
                                .content("반포자이 " + i)
                                .build())
                        .collect(Collectors.toList())
        ); // 인트스트림을 통한 반복문
        postRepository.saveAll(requestPosts);

        //when
        mockMvc.perform(get("/posts?page=0&size=5")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("이상 제목 30"))
                .andExpect(jsonPath("$[0].content").value("반포자이 30"))
                .andDo(print());
    }


    @Test
    @DisplayName("글제목 수정")
    void editPost() throws Exception {
        //given

        Post post = Post.builder()
                .title("내 이름은 코난")
                .content("탐정이죠")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("이상")
                .content("반포자이")
                .build();


        //when
        mockMvc.perform(patch("/posts/{postId}", post.getId()) //PATCH /posts/{postId}
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePost() throws Exception {
        //given
        Post post = Post.builder()
                .title("이상")
                .content("반포자이")
                .build();

        postRepository.save(post);

        mockMvc.perform(delete("/posts/{postId}", post.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 삭제")
    void deleteNothing() throws Exception{

        mockMvc.perform(delete("/posts/{postId}", 1L)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    @DisplayName("존재하지 않는 게시글 수정")
    void patchNothing() throws Exception{

// Given
        PostEdit postEdit = PostEdit.builder()
                .title("이상")
                .content("반포자이")
                .build();
//expected
        mockMvc.perform(patch("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(status().isNotFound())
                .andDo(print());

    }


    @Test
    @DisplayName("/게시글 등록시 제목에 '바보'는 포함될 수 없다.")
    void invalidRequestTest() throws Exception {



        // given
        //PostCreate request = new PostCreate("제목입니다.", "내용입니다.");
        // 생성자를 통한 주입의 경우엔 추후에 비롯되는 에러를 파악하기 힘들어짐 (생성자에서의 파라미터 순서를 변경하는 경우) -> 빌더를 써야하는 이유

        PostCreate request = PostCreate.builder()
                .title("바보입니다.")
                .content("내용입니다.")
                .build();
        // context 에 담아줄 수 있게끔 ObjectMapper 를 json string 형태로 변경해줌 (ObjectMapper 는 매우 자주 쓰이므로 꼭 기억)

        String json = objectMapper.writeValueAsString(request);// 추후에 테스트 케이스를 수정하기 용이해짐

        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}