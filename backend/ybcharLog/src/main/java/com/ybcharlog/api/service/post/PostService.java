package com.ybcharlog.api.service.post;

import com.ybcharlog.api.RequestDto.post.PostCreateDto;
import com.ybcharlog.api.RequestDto.post.PostEditDto;
import com.ybcharlog.api.RequestDto.post.PostSearchDto;
import com.ybcharlog.api.ResponseDto.post.PostResponse;
import com.ybcharlog.api.domain.post.Post;
import com.ybcharlog.api.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public Post write(PostCreateDto postCreateDto) {
		return postRepository.save(Post.initPost(postCreateDto.getTitle(), postCreateDto.getContent()));
	}

	public PostResponse getOne(Long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

		return PostResponse.builder()
				.id(post.getId())
				.title(post.getTitle())
				.content(post.getContent())
				.viewCount(post.getViewCount())
				.build();
	}

	public List<PostResponse> getList(PostSearchDto postSearchDto) {
		// Pageable 한번 까서 볼것!
//		Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());

		// 순수 JPA
//		return postRepository.findAll(pageable).stream()
//				.map(PostResponse::new
//				)
//				.collect(Collectors.toList());
		// Querydsl
		return postRepository.getList(postSearchDto).stream()
				.map(PostResponse::new)
				.collect(Collectors.toList());
	}

	@Transactional
	public void editPost(Long postId, PostEditDto postEditDto) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
//		PostEditorBuilder editorBuilder = post.toEditor();
//
//		PostEditor postEditor = editorBuilder.title(postEditDto.getTitle())
//				.content(postEditDto.getContent())
//				.build();
		post.edit(postEditDto, post);
//		postRepository.editPost(postId, postEditDto);
	}

	public void deletePost(Long postId) {
		postRepository.deleteById(postId);
	}
}
