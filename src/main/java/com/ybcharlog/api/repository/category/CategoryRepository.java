package com.ybcharlog.api.repository.category;

import com.ybcharlog.api.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom, JpaSpecificationExecutor<Category> {
}
