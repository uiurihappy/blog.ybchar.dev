package com.ybcharlog.api.service;

import com.ybcharlog.api.RequestDto.PostCreateDto;
import com.ybcharlog.api.domain.Post;
import com.ybcharlog.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public void write(PostCreateDto postCreateDto) {
		// repository.save(postCreate)
		// postCreateDto -> Post
//		Post post = new Post(postCreateDto.getTitle(), postCreateDto.getContent());
		Post post = Post.builder()
				.title(postCreateDto.getTitle())
				.content(postCreateDto.getContent())
				.build();

		postRepository.save(post);
	}

	public Post getOne(Long id) {
		return postRepository.findById(id) // warning
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
	}
}
