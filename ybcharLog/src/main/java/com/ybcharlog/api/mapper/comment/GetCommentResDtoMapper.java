package com.ybcharlog.api.mapper.comment;


import com.ybcharlog.api.Common.mapper.GenericMapper;
import com.ybcharlog.api.ResponseDto.comment.CommentResponse;
import com.ybcharlog.api.domain.comment.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GetCommentResDtoMapper extends GenericMapper<CommentResponse, Comment> {

	GetCommentResDtoMapper INSTANCE = Mappers.getMapper(GetCommentResDtoMapper.class);

	@Override
	CommentResponse toDto(Comment entity);

	@Override
	default List<CommentResponse> toDtoList(final Collection<Comment> entities) {
		return GenericMapper.super.toDtoList(entities);
	}
}
