package com.example.onlinestore.mapper;

import com.example.onlinestore.dto.RegisterReq;
import com.example.onlinestore.dto.User;
import com.example.onlinestore.model.UserModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {


    User toUser(RegisterReq registerReq);

    @Mapping(source = "username", target = "email")
    @Mapping(target = "image", expression = "java(getImageModel(userModel))")
    void toUserDto(@MappingTarget User user, UserModel userModel);

    default String getImage(UserModel userModel) {
        if (userModel.getImageModel() == null) {
            return null;
        }
        return "/users/image/" + userModel.getId() + "/from-db";
    }

    @Mapping(ignore = true, target = "userModel.id")
    @Mapping(ignore = true, target = "userModel.imageModel")
    @Mapping(ignore = true, target = "userModel.username")
    void toUserModel(@MappingTarget UserModel userModel, User user);
}
