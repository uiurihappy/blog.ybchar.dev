package com.ybcharlog.api.service;

import com.ybcharlog.api.RequestDto.PostCreateDto;
import com.ybcharlog.api.ResponseDto.PostResponse;
import com.ybcharlog.api.domain.Post;
import com.ybcharlog.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public Post write(PostCreateDto postCreateDto) {
		// repository.save(postCreate)
		// postCreateDto -> Post
//		Post post = new Post(postCreateDto.getTitle(), postCreateDto.getContent());

		return postRepository.save(Post.initPost(postCreateDto.getTitle(), postCreateDto.getContent()));
	}

	public PostResponse getOne(Long id) {
		Post post = postRepository.findById(id) // warning
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

		return PostResponse.builder()
				.id(post.getId())
				.title(post.getTitle())
				.content(post.getContent())
				.viewCount(post.getViewCount())
				.build();
	}

	public List<PostResponse> getList(int page) {
		// Pageable 한번 까서 볼것!
		Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
		return postRepository.findAll(pageable).stream()
				.map(PostResponse::new
				)
				.collect(Collectors.toList());
	}
}