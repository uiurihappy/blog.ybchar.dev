package com.ybcharlog.api.service;

import com.ybcharlog.api.RequestDto.PostCreateDto;
import com.ybcharlog.api.domain.Post;
import com.ybcharlog.api.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
				.build();
		postRepository.save(savePost);

		// when
		Post post = postService.getOne(savePost.getId());

		// then
		assertNotNull(post);
		assertEquals(1L, postRepository.count());
		assertEquals("foo", post.getTitle());
		assertEquals("bar", post.getContent());
	}
}