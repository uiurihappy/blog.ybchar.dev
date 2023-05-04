package com.ybcharlog.api.service.category;

import com.ybcharlog.api.domain.category.Category;
import com.ybcharlog.api.repository.category.CategoryRepository;
import com.ybcharlog.api.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

}
