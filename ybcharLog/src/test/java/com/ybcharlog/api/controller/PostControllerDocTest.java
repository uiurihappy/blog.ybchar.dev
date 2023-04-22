package com.ybcharlog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybcharlog.api.RequestDto.post.PostCreateDto;
import com.ybcharlog.api.ResponseDto.comment.CommentResponse;
import com.ybcharlog.api.domain.comment.Comment;
import com.ybcharlog.api.domain.post.Post;
import com.ybcharlog.api.mapper.comment.GetCommentResDtoMapper;
import com.ybcharlog.api.repository.comment.CommentRepository;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.Optional;

import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
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
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

//    @BeforeEach
//    public void setUp(WebApplicationContext webApplicationContext,
//                      RestDocumentationContextProvider restDocumentation) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(documentationConfiguration(restDocumentation))
//                .build();
//    }

//    @BeforeEach
//    public void deleteAll() {
//        postRepository.deleteAll();
//    }

    @Test
    @DisplayName("getOnePost Restdocs")
    void getOnePostTest() throws Exception {

        // given
        Post post = Post.builder()
                .title("글 제목입니다.")
                .content("글 내용입니다.")
                .viewCount(0)
                .likeCount(0)
                .display(1)
                .isDeleted(0)
                .build();
        postRepository.save(post);
//        CommentResponse postComment = GetCommentResDtoMapper.INSTANCE.toDto(commentRepository.save(Comment.initComment("나 유저 1", null
//                , "유저 1의 댓글", 0, 1, 0, post)));

        // expected
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{postId}", post.getId())
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("getOnePost",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").description("게시글 ID"),
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용"),
                                fieldWithPath("viewCount").description("게시글 조회 수"),
                                fieldWithPath("likeCount").description("게시글 좋아요 수"),
                                fieldWithPath("display").description("게시글 viewStatus"),
                                fieldWithPath("isDeleted").description("게시글 삭제 상태"),

                                fieldWithPath("comments.[]").type(JsonFieldType.ARRAY).description("댓글"),
//                                fieldWithPath("comments.[].id").description("댓글 ID"),
//                                fieldWithPath("comments.[].username").description("댓글 사용자 이름"),
//                                fieldWithPath("comments.[].password").description("댓글 비밀번호").optional(),
//                                fieldWithPath("comments.[].commentContent").description("댓글 내용"),
//                                fieldWithPath("comments.[].secretStatus").description("댓글 비밀번호 상태"),
//                                fieldWithPath("comments.[].display").description("게시글 viewStatus"),
//                                fieldWithPath("comments.[].isDeleted").description("게시글 삭제 상태"),
//                                fieldWithPath("comments.[].createdAt").description("생성 일자"),
//                                fieldWithPath("comments.[].lastModifiedDate").description("수정 일자"),
//                                fieldWithPath("comments.[].createdBy").description("생성한 유저 ID"),
//                                fieldWithPath("comments.[].lastModifiedBy").description("수정한 유저 ID"),
                                fieldWithPath("createdAt").description("생성 일자"),
                                fieldWithPath("lastModifiedDate").description("수정 일자")
//                                fieldWithPath("createdBy").description("생성한 유저 ID"),
//                                fieldWithPath("lastModifiedBy").description("수정한 유저 ID")
                        ))
                );

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
                .display(0)
                .isDeleted(0)
                .build();
        String json = objectMapper.writeValueAsString(request);     // Javascript의 JSON.stringfy(object) 느낌

        // expected
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/posts/save")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(document("savePost",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").description("게시글 제목")
                                        .attributes(Attributes.key("constraint").value("좋은 제목 입력해주세요")),
                                fieldWithPath("content").description("게시글 내용"),
                                fieldWithPath("viewCount").description("게시글 조회 수").optional(),
                                fieldWithPath("likeCount").description("게시글 좋아요 수").optional(),
                                fieldWithPath("display").description("게시글 viewStatus"),
                                fieldWithPath("isDeleted").description("게시글 삭제 상태")
                        )
                ));
    }

    @Test
    @DisplayName("deletePost Restdocs")
    void deletePostTest() throws Exception {
        // given
        Post post = Post.builder()
                .title("글 제목입니다.")
                .content("글 내용입니다.")
                .viewCount(0)
                .likeCount(0)
                .display(1)
                .isDeleted(0)
                .build();
        postRepository.save(post);

//        String json = objectMapper.writeValueAsString(post);     // Javascript의 JSON.stringfy(object) 느낌
//        System.out.println(json);
//        Optional<Post> findPost = postRepository.findById(1L);

        // expected
        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/posts/delete/{postId}", post.getId())
                        .accept(APPLICATION_JSON))
//                        .contentType(APPLICATION_JSON)
//                        .content(json))
                .andExpect(status().isOk())
                .andDo(document("deletePost",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        )
//                        relaxedRequestFields()
                ));
    }
}
