package com.example.onlinestore.mapper;

import com.example.onlinestore.dto.User;
import com.example.onlinestore.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );
    User userToDto(UserModel userModel);
    UserModel userToModel(User user);
}
