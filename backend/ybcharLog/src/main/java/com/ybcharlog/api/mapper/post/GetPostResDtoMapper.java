package com.ybcharlog.api.mapper.post;

import com.ybcharlog.api.Common.mapper.GenericMapper;
import com.ybcharlog.api.ResponseDto.post.PostResponse;
import com.ybcharlog.api.domain.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.*;

@Mapper(componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GetPostResDtoMapper extends GenericMapper<PostResponse, Post> {

	GetPostResDtoMapper INSTANCE = Mappers.getMapper(GetPostResDtoMapper.class);

	@Override
	PostResponse toDto(Post entity);

	@Override
	default List<PostResponse> toDtoList(final Collection<Post> entities) {
		return GenericMapper.super.toDtoList(entities);
	}
}
