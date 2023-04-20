package com.ybcharlog.api.domain.category;

import com.ybcharlog.api.Common.BaseEntity;
import com.ybcharlog.api.domain.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

	@Id
	private Long categoryId;

	@OneToMany(mappedBy = "category", orphanRemoval = true)
	private List<Post> posts = new ArrayList<>();

	@Column(columnDefinition = "varchar(30) COMMENT '카테고리 depth1'")
	private String depth1;

	@Column(columnDefinition = "varchar(30) COMMENT '카테고리 depth2'")
	private String depth2;

	@Column(columnDefinition = "varchar(30) COMMENT '카테고리 depth3'")
	private String depth3;

}