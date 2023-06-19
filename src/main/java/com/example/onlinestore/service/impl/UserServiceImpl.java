package com.example.onlinestore.service.impl;

import com.example.onlinestore.dto.User;
import com.example.onlinestore.exception.NotFoundUserException;
import com.example.onlinestore.exception.NullEmailException;
import com.example.onlinestore.mapper.UserMapper;
import com.example.onlinestore.model.UserModel;
import com.example.onlinestore.repository.UserRepository;
import com.example.onlinestore.service.ImageService;
import com.example.onlinestore.service.UserService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.util.ObjectUtils.isEmpty;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, ImageService imageService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.imageService = imageService;
    }

    @Override
    public User update(User user, String email) throws IOException {
        if (isEmpty(email)) {
            throw new NullEmailException();
        }
        user.setEmail(email);
        UserModel userModel = userRepository.findUserByEmail(email).orElse(new UserModel());
        UserMapper.INSTANCE.partialUpdate(user, userModel);
        return UserMapper
                .INSTANCE
                .userModelToUser(userRepository.save(userModel));
    }

    @Override
    public User getUser(String email) throws IOException {
        UserModel user = userRepository.findUserByEmail(email).orElseThrow(NotFoundUserException::new);
        return UserMapper
                .INSTANCE
                .userModelToUser(user);
    }

    @Override
    public void updateAvatar(MultipartFile image, String name) throws IOException {

    }

    @Override
    public byte[] getImage(int id) throws IOException {
        return new byte[0];
    }

}
