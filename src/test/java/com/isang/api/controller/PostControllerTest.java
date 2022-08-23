package com.isang.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isang.api.domain.Post;
import com.isang.api.repository.PostRepository;
import com.isang.api.request.PostCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        Post post1 = Post.builder()
                .title("foo1")
                .content("bar1")
                .build();
        postRepository.save(post1);

        Post post2 = Post.builder()
                .title("foo2")
                .content("bar2")
                .build();
        postRepository.save(post2);

        //when
        mockMvc.perform(get("/posts")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                /**
                 * {id : ... , title : ...}
                 */

                /**
                 * [{id : ... , title : ...} {id : ... , title : ...}
                 */
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].id").value(post1.getId()))
                .andExpect(jsonPath("$[0].title").value(post1.getTitle()))
                .andExpect(jsonPath("$[0].content").value(post1.getContent()))
                .andDo(print());
    }

}