package com.example.onlinestore.mapper;

import com.example.onlinestore.dto.Comment;
import com.example.onlinestore.model.CommentModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper( CommentMapper.class );
    Comment commentToDto (CommentModel commentModel);
    CommentModel commentToModel(Comment comment);
}
