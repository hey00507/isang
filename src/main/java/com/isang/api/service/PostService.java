package com.isang.api.service;

import com.isang.api.domain.Post;
import com.isang.api.repository.PostRepository;
import com.isang.api.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {


    private final PostRepository postRepository;


    public void write(PostCreate postCreate){

        //postCreate -> Entity

        Post post =Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }
}
