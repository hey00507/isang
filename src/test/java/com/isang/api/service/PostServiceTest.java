package com.isang.api.service;

import com.isang.api.domain.Post;
import com.isang.api.repository.PostRepository;
import com.isang.api.request.PostCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(requestPost); // save 메서드가 실행되면, 기존의 null 이던 requsetPost 의 id 값이, pk 값에 맡게 새로 set 된다.


        // when
        Post post = postService.get(requestPost.getId());

        assertNotNull(post);
        assertEquals(1L,postRepository.count());
        assertEquals("foo", post.getTitle());
        assertEquals("bar", post.getContent());

    }
}