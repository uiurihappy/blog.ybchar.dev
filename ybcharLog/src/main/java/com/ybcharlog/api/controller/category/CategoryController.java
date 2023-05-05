package com.ybcharlog.api.controller.category;

import com.ybcharlog.api.RequestDto.category.CategoryCreateDto;
import com.ybcharlog.api.domain.category.Category;
import com.ybcharlog.api.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/save")
    public Category save(CategoryCreateDto categoryCreateDto) {
        return categoryService.save(categoryCreateDto);
    }
}
