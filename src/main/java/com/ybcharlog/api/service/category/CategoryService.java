package com.ybcharlog.api.service.category;

import com.ybcharlog.api.dto.RequestDto.category.CategoryCreateDto;
import com.ybcharlog.api.domain.category.Category;
import com.ybcharlog.api.repository.category.CategoryRepository;
import com.ybcharlog.api.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    public Category save(CategoryCreateDto categoryCreateDto) {
        return categoryRepository.save(Category.initCategory(
                categoryCreateDto.getDepth1(),
                categoryCreateDto.getDepth2(),
                categoryCreateDto.getDepth3(),
                categoryCreateDto.getPosts()
        ));
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
