package com.isang.api.service;

import com.isang.api.domain.Post;
import com.isang.api.repository.PostRepository;
import com.isang.api.request.PostCreate;
import com.isang.api.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class PostServiceTest {


    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;


    @BeforeEach // 테스트가 실행되기전에 모든 DB 를 비워줌
    void cleanPosts(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void write(){

        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();




        //when
        postService.write(postCreate);


        //then
        assertEquals(1L,postRepository.count()); // DB 에 저장됐는지 유무

        Post post = postRepository.findAll().get(0); // DB 에 저장된 값이 넣은 값과 일치하는지 유무

        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }


    @Test
    @DisplayName("글을 한 개 조회한다.")
    void read(){
        //given
        Post requestPost = Post.builder()
                .title("foo1234567890123")
                .content("bar")
                .build();
        /*
         * 클라이언트 요구사항
         * Json 응답에서 title 값이 최대 10글자만 되게끔 해달라 -> 원래는 클라이언트 단에서 필터링해주는 것이 맞음
         * Post Class 와 PostResponse 의 필드가 같기에 문제가 될 부분이 크게 없음
         * */


        postRepository.save(requestPost); // save 메서드가 실행되면, 기존의 null 이던 requsetPost 의 id 값이, pk 값에 맡게 새로 set 된다.


        // when
        PostResponse response = postService.get(requestPost.getId());

        assertNotNull(response);
        assertEquals(1L,postRepository.count());
        assertEquals("foo1234567", response.getTitle());
        assertEquals("bar", response.getContent());

    }

    @Test
    @DisplayName("여러 개의 글을 조회한다.")
    void getList(){
        //given
        Post requestPost = Post.builder()
                .title("foo1")
                .content("bar1")
                .build();

        Post requestPost2 = Post.builder()
                .title("foo2")
                .content("bar2")
                .build();

        postRepository.save(requestPost);
        postRepository.save(requestPost2);


        // when
        List<PostResponse> postResponses = postService.getList();


        assertEquals(2L , postRepository.count());
        assertEquals(2L , postResponses.size());
    }
}