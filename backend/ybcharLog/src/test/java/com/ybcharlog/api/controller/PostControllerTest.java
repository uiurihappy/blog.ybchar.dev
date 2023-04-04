package com.ybcharlog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybcharlog.api.RequestDto.PostCreateDto;
import com.ybcharlog.api.domain.Post;
import com.ybcharlog.api.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    // 다른 테스트에 영향이 가지 않도록 사전에 deleteAll한다.
    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 호출 시 hello world 출력")
    void test() throws Exception {
        // given
//        PostCreateDto request = new PostCreateDto("글 제목입니다", "글 내용입니다");
        PostCreateDto request = PostCreateDto.builder()
                .title("글 제목입니다.")
                .content("글 내용입니다.")
                .build();
        String json = objectMapper.writeValueAsString(request);     // Javascript의 JSON.stringfy(object) 느낌
//        System.out.println(json);

        // expected
        mockMvc.perform(post("/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());

        // DB에 1개 저장
    }

    @Test
    @DisplayName("/posts 요청 시 title값은 필수다.")
    void test2() throws Exception {
        // given
        PostCreateDto request = PostCreateDto.builder()
                .content("글 내용입니다.")
                .build();
        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isBadRequest())
//                .andExpect(content().string("{}"))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("title input please"))
                .andDo(print());

    }

    @Test
    @DisplayName("/posts 요청 시 DB 값 저장 테스트")
    void postWriteTest() throws Exception {
        // given
        PostCreateDto request = PostCreateDto.builder()
                .title("글 제목 test")
                .content("글 내용 test")
                .build();
        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, postRepository.count());

        // 전체 테스트 시에 첫 테스트에서 insert 했기 때문에 2개로 뜬다.
        // 그래서 다른 테스트에 영향이 가지 않도록 짜야 한다.

        Post post = postRepository.findAll().get(0);
        assertEquals("글 제목 test", post.getTitle());
        assertEquals("글 내용 test", post.getContent());
    }

}