package com.example.onlinestore.mapper;

import com.example.onlinestore.dto.Comment;
import com.example.onlinestore.model.CommentModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "id", target = "pk")
    Comment commentModelToComment(CommentModel commentModel);

    @Mapping(target = "id", source = "pk")
    CommentModel commentToCommentModel(Comment comment);
}
