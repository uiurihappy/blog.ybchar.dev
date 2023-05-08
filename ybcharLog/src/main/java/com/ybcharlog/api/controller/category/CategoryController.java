package com.ybcharlog.api.controller.category;

import com.ybcharlog.api.RequestDto.category.CategoryCreateDto;
import com.ybcharlog.api.domain.category.Category;
import com.ybcharlog.api.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public Category save(CategoryCreateDto categoryCreateDto) {
        return categoryService.save(categoryCreateDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{categoryId}")
    public void delete(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
