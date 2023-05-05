package com.ybcharlog.api.controller.category;

import com.ybcharlog.api.RequestDto.category.CategoryCreateDto;
import com.ybcharlog.api.domain.category.Category;
import com.ybcharlog.api.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/delete/{categoryId}")
    public void delete(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
