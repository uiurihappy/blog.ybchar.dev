package com.ybcharlog.api.domain.post;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import com.ybcharlog.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = {@Index(name = "idx_created_by", columnList = "createdBy")})
public class PostImage extends BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "bigint unsigned not null COMMENT '업로드파일 ID'")
	private Long id;

	@Column(columnDefinition = "text COMMENT '원본 파일 이름'")
	private String originalFilename;

	@Column(columnDefinition = "text COMMENT '파일 경로'")
	private String filePath;

	@Column(columnDefinition = "text COMMENT '파일 URL'")
	private String fileUrl;

	@Column(columnDefinition = "bigint unsigned COMMENT '파일 크기'")
	private Long fileSize;
}
