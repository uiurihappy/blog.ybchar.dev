package com.ybcharlog.api.repository.category;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ybcharlog.common.repository.BasicRepoSupport;
import com.ybcharlog.api.domain.category.QCategory;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

@Repository
public class CategoryRepositoryImpl extends BasicRepoSupport implements CategoryRepositoryCustom {

    private static final QCategory category = QCategory.category;
    protected CategoryRepositoryImpl(JPAQueryFactory jpaQueryFactory, EntityManager em) {
        super(jpaQueryFactory, em);
    }
}
