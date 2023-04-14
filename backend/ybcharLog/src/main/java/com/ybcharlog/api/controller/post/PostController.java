package com.ybcharlog.api.controller.post;

import com.querydsl.core.Tuple;
import com.ybcharlog.api.Common.dto.CustomPage;
import com.ybcharlog.api.RequestDto.post.PostCreateDto;
import com.ybcharlog.api.RequestDto.post.PostEditDto;
import com.ybcharlog.api.RequestDto.post.PostSearchDto;
import com.ybcharlog.api.ResponseDto.post.PostResponse;
import com.ybcharlog.api.domain.post.Post;
import com.ybcharlog.api.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.ybcharlog.api.RequestDto.post.PostSearchDto.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    /* HTTP methods
     * GET, POST, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
     * 글 등록 - POST
     */
    /* 데이터를 검증하는 이유 (Why?)
            1. 클라이언트 개발자가 깜빡할 수도 있음
            2. 클라이언트 개발자의 버그로 값이 누락될 수도 있다.
            3. 외부에서 값을 임의로 조작할 여부가 있다.
            4. DB에 값을 저장할 때 의도치 않은 값이 들어갈 수도 있다.
            5. 서버 개발자의 편안함을 위해?

            검증 컨벤션
            1. 응답값에 HashMap -> 응답 클래스를 만들어주는 것이 좋다.
            2. 여러 개의 에러 처리
            3. 세 번 이상의 반복적인 작업은 피해야한다.
                // 코드 && 개발에 관해 모든 것이 필요
                    // 세 번 이상이면 적어도 자동화를 고려해볼 것
    */
    private final PostService postService;

    /* response case
		1. 저장한 데이터 Entity 통째로 response
		2. 저장한 데이터의 primary_id만 response
			- Client는 받은 id를 post 조회 시 API를 통해서 데이터를 수신
		3. 응답 필요없음 (return Type x, void) -> Client에서 모든 POST 데이터 context를 관리함
		개발하면서 당연히 "반드시 fix"라는 것이 없기에 유연하게 반응할 수 있도록 대응하는 것이 좋다.
			- 즉, 잘 관리하는 형태로 구현하는 것이 좋다.
	*/
    @PostMapping("/posts")
    public Post post(@RequestBody @Valid PostCreateDto request)  {
        return postService.write(request);
    }

    /*
        /posts -> 글 전체 조회 (검색 + 페이징)
        /posts/{postId} -> 글 한개만 조회
     */
    @GetMapping("/posts")
    public ResponseEntity<CustomPage<PostResponse>> getPostList(GetPostPageReq req, Pageable pageable) {
        // 페이징 처리가 필요 -> response 비용이 많이 들기 때문이다.
        // -> 통신, 트래픽 비용이 많아지면 응답 속도 시간뿐만 아니라 직접 겪어봐서 아는데 DB까지 터진다.
        return ResponseEntity.ok(postService.getList(req, pageable));
    }
    @GetMapping("/posts/{postId}")
    public Post getOne(@PathVariable Long postId) {
        // 서비스 정책에 맞는 응답 클래스를 분리하는 것이 옳다.
        return postService.getOne(postId);
    }

    @PatchMapping("/posts/{postId}")
    public void editPost(@PathVariable Long postId, @RequestBody @Valid PostEditDto postEditDto) {
        postService.editPost(postId, postEditDto);
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}
