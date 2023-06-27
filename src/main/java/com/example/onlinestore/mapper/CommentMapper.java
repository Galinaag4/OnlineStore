package com.example.onlinestore.mapper;

import com.example.onlinestore.dto.Comment;
import com.example.onlinestore.dto.CreateComment;
import com.example.onlinestore.model.CommentModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Mapper(componentModel = "spring")
public interface CommentMapper {


    CommentModel toCommentModel(CreateComment createComment);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "userModel.id")
    @Mapping(target = "authorFirstName", source = "userModel.firstName")
    @Mapping(target = "authorImage", expression = "java(getImageModel(comment))")
    Comment toComment(CommentModel commentModel);

    default String getImageModel(CommentModel commentModel) {
        if (commentModel.getUserModel().getImageModel() == null) {
            return null;
        }
        return "/users/image/" + commentModel.getUserModel().getId() + "/from-db";
    }

    List<Comment> commentListToCommentDtoList(List<CommentModel> commentList);
}
