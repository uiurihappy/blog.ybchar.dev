package com.ybcharlog.api.service;

import com.ybcharlog.api.RequestDto.PostCreateDto;
import com.ybcharlog.api.RequestDto.PostSearchDto;
import com.ybcharlog.api.ResponseDto.PostResponse;
import com.ybcharlog.api.domain.Post;
import com.ybcharlog.api.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
				.size(10)
				.build();

		List<PostResponse> posts = postService.getList(postSearchDto);

		// then
		assertEquals(10L, posts.size());
		assertEquals("ybchar title 19", posts.get(0).getTitle());
		assertEquals("ybchar title 15", posts.get(4).getTitle());

	}

}