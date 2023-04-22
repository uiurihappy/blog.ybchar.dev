package com.ybcharlog.api.service;

import com.ybcharlog.api.RequestDto.post.PostCreateDto;
import com.ybcharlog.api.RequestDto.post.PostEditDto;
import com.ybcharlog.api.RequestDto.post.PostSearchDto;
import com.ybcharlog.api.ResponseDto.post.PostResponse;
import com.ybcharlog.api.domain.post.Post;
import com.ybcharlog.api.repository.post.PostRepository;
import com.ybcharlog.api.service.post.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

	@Autowired
	private PostService postService;
	@Autowired
	private PostRepository postRepository;

	@BeforeEach
	void clean() {
		postRepository.deleteAll();
	}

	@Test
	@DisplayName("글 작성")
	void postTest1() {
		// given
		PostCreateDto postCreateDto = PostCreateDto.builder()
				.title("글 제목입니다.")
				.content("글 내용입니다.")
				.build();

		// when
		postService.write(postCreateDto);

		// then
		assertEquals(1L, postRepository.count());
		Post post = postRepository.findAll().get(0);
		assertEquals("글 제목입니다.", post.getTitle());
		assertEquals("글 내용입니다.", post.getContent());
	}

	@Test
	@DisplayName("글 단건 조회")
	void getOneTest1() {
		// given
		Post savePost = Post.builder()
				.title("foo")
				.content("bar")
				.viewCount(0)
				.likeCount(0)
				.build();
		postRepository.save(savePost);

		// when
		PostResponse post = postService.getOne(savePost.getId());

		// then
		assertNotNull(post);
		assertEquals(1L, postRepository.count());
		assertEquals("foo", post.getTitle());
		assertEquals("bar", post.getContent());
	}

	@Test
	@DisplayName("글 1페이지 조회")
	void getListTest1() {
		// given
		List<Post> requestPosts = IntStream.range(0, 20)
				.mapToObj(i -> Post.builder()
						.title("ybchar title " + i)
						.content("ybchar content " + i)
						.viewCount(0)
						.likeCount(0)
						.build())
				.collect(Collectors.toList());
		postRepository.saveAll(requestPosts);

		// sql -> select ... limit, offset

		// when
		PostSearchDto postSearchDto = PostSearchDto.builder()
				.page(1)
				.build();

		List<PostResponse> posts = postService.getList(postSearchDto);

		// then
		assertEquals(10L, posts.size());
		assertEquals("ybchar title 19", posts.get(0).getTitle());
		assertEquals("ybchar title 15", posts.get(4).getTitle());

	}

	@Test
	@DisplayName("글 제목 수정")
	void editTitlePost1() {
		// given
		Post post = Post.builder()
				.title("ybchar title 1")
				.content("ybchar content 1")
				.viewCount(0)
				.likeCount(0)
				.build();
		postRepository.save(post);

		PostEditDto postEditDto = PostEditDto.builder()
				.title("ybchar edit title test1")
				.content("ybchar edit content test1")
				.build();
		// when
		postService.editPost(post.getId(), postEditDto);

		// then
		Post changedPost = postRepository.findById(post.getId())
				.orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));
		assertEquals("ybchar edit title test1", changedPost.getTitle());

	}

	@Test
	@DisplayName("글 내용 수정")
	void editTitlePost2() {
		// given
		Post post = Post.builder()
				.title("ybchar title 1")
				.content("ybchar content 1")
				.viewCount(0)
				.likeCount(0)
				.build();
		postRepository.save(post);

		PostEditDto postEditDto = PostEditDto.builder()
				.content("ybchar edit content test1")
				.build();


		// when
		postService.editPost(post.getId(), postEditDto);
//		PostResponse post1 = postService.getOne(1L);
//		System.out.println(post1.getTitle());
//		System.out.println(post1.getContent());

		// then
		Post changedPost = postRepository.findById(post.getId())
				.orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));
		assertEquals("ybchar title 1", changedPost.getTitle());

	}

	@Test
	@DisplayName("글 단건 삭제")
	void deletePostTest1() {
		// given
		Post post = Post.builder()
				.title("ybchar title 1")
				.content("ybchar content 1")
				.viewCount(0)
				.likeCount(0)
				.build();
		postRepository.save(post);

		// when
		postService.deletePost(post.getId());

		// then
		assertEquals(0, postRepository.count());
	}

	@Test
	@DisplayName("글 단건 조회 실패 케이스")
	void getOneFailTest() {
		// given
		Post savePost = Post.builder()
				.title("foo")
				.content("bar")
				.viewCount(0)
				.likeCount(0)
				.build();
		postRepository.save(savePost);

		// expected
		InvalidDataAccessApiUsageException e = assertThrows(InvalidDataAccessApiUsageException.class, () -> {
			postService.getOne(savePost.getId() + 1L);
		});

		assertEquals("존재하지 않는 글입니다.", e.getMessage());
	}

	@Test
	@DisplayName("게시글 삭제 - 존재하지 않는 글")
	void NotFoundPostDelete() {
		// given
		Post post = Post.builder()
				.title("test1")
				.content("test Content1")
				.viewCount(0)
				.likeCount(0)
				.isDeleted(0)
				.display(1)
				.build();
		postRepository.save(post);

		// when
		postService.deletePost(post.getId() + 1L);

		// then
		assertEquals(0, postRepository.count());
	}
}