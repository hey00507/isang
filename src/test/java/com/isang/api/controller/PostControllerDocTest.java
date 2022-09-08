package com.isang.api.controller;

import com.isang.api.request.PostCreate;
import com.isang.api.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.isang.com", uriPort = 443)
public class PostControllerDocTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;



    @Test
    @DisplayName("글 단건 조회 테스트")
    void getPostRestDocTest() throws Exception {

        //given
        PostCreate postCreate =
                PostCreate
                        .builder()
                        .title("하이염")
                        .content("바이염")
                        .build();


        postService.write(postCreate);


        //expected
        this.mockMvc.perform(get("/posts/{postId}",1L)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("index", pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        )
                ));
    }

}
