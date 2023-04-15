package com.ybcharlog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybcharlog.api.RequestDto.post.PostCreateDto;
import com.ybcharlog.api.domain.post.Post;
import com.ybcharlog.api.repository.post.PostRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.ybchar.dev", uriPort = 3002)
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

//    @BeforeEach
//    public void setUp(WebApplicationContext webApplicationContext,
//                      RestDocumentationContextProvider restDocumentation) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(documentationConfiguration(restDocumentation))
//                .build();
//    }

    @Test
    @DisplayName("getOnePost Restdocs")
    void getOnePostTest() throws Exception {
        // expected
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{postId}", 1L)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("getOnePost",
                        requestFields(
                                fieldWithPath("postId").description("게시글 Id")
                        ),
                        responseFields(
                                fieldWithPath("list").description("게시글 리스트"),
                                fieldWithPath("totalElements").description("총 게시글 수"),
                                fieldWithPath("totalCount").description("총 페이지 수")
                        )
                ));
    }

    @Test
    @DisplayName("savePost Restdocs")
    void savePostTest() throws Exception {
        // given
        PostCreateDto request = PostCreateDto.builder()
                .title("글 제목입니다.")
                .content("글 내용입니다.")
                .viewCount(0)
                .likeCount(0)
                .build();
        String json = objectMapper.writeValueAsString(request);     // Javascript의 JSON.stringfy(object) 느낌

        // expected
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/posts/save")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(document("savePost",
                        requestFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용"),
                                fieldWithPath("viewCount").description("게시글 조회 수"),
                                fieldWithPath("likeCount").description("게시글 좋아요 수")
                        )
                ));
    }
}
