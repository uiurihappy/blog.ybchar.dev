package com.ybcharlog.api.service;

import com.ybcharlog.api.RequestDto.PostCreateDto;
import com.ybcharlog.api.domain.Post;
import com.ybcharlog.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public void write(PostCreateDto postCreateDto) {
		// repository.save(postCreate)
		// postCreateDto ->
		Post post = new Post(postCreateDto.getTitle(), postCreateDto.getContent());

		postRepository.save(post);
	}
}
