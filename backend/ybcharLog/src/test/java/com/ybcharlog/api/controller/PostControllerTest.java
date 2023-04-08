package com.ybcharlog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybcharlog.api.RequestDto.PostCreateDto;
import com.ybcharlog.api.domain.Post;
import com.ybcharlog.api.repository.PostRepository;
import org.hamcrest.Matchers;
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

import java.util.List;

import static org.hamcrest.Matchers.*;
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

    @Deprecated
    @Test
    @DisplayName("/posts 호출 시 hello world 출력")
    void test() throws Exception {
        // given
//        PostCreateDto request = new PostCreateDto("글 제목입니다", "글 내용입니다");
        PostCreateDto request = PostCreateDto.builder()
                .title("글 제목입니다.")
                .content("글 내용입니다.")
                .viewCount(0)
                .likeCount(0)
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
                .viewCount(0)
                .likeCount(0)
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
        assertEquals(0, post.getViewCount());
    }

    @Test
    @DisplayName("글 단건 조회")
    void getOneTest1() throws Exception {
        // given
        Post post = Post.builder()
                .title("foofoofoofoofoo")
                .content("bar")
                .viewCount(0)
                .likeCount(0)
                .build();
        postRepository.save(post);
        System.out.println("postId = " + post.getId());
        // expected
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("foofoofoof"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andExpect(jsonPath("$.viewCount").value(0))
                .andDo(print());
    }

    @Test
    @DisplayName("사용자 글 리스트 조회")
    void getListTest() throws Exception {
        // given
        for (int i = 1; i <= 5; i++) {
            Post savePost = Post.builder()
                    .title("foo" + i)
                    .content("bar" + i)
                    .viewCount(0)
                    .likeCount(0)
                    .build();
            postRepository.save(savePost);
        }

        List<Post> posts = postRepository.findAll();

        // expected
        mockMvc.perform(get("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                /**
                 * [{""}, {""}]
                 */
                // Matchers 사용
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$.[0].id").value(posts.get(0).getId()))
                .andExpect(jsonPath("$.[0].title").value("foo1"))
                .andExpect(jsonPath("$.[0].content").value("bar1"))
                .andDo(print());

    }

}