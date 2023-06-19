package com.example.onlinestore.mapper;

import com.example.onlinestore.dto.RegisterReq;
import com.example.onlinestore.dto.User;
import com.example.onlinestore.model.UserModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.io.IOException;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "image", expression = "java(getImage(profileUser))")
    User userModelToUser(UserModel userModel) throws IOException;

    default String getImage(UserModel userModel) throws IOException {
        if (userModel.getImage()==null||userModel.getImage().isEmpty()){
            return null;
        }
        return "/users/"+userModel.getId()+"/getImage";
    }


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "image",ignore = true)
    void partialUpdate(User user, @MappingTarget UserModel userModel);


    @Mapping(source = "username", target = "email")
    UserModel registerReqToUser(RegisterReq registerReq);
}
