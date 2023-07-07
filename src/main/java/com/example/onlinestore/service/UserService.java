package com.example.onlinestore.service;

import com.example.onlinestore.dto.*;
import com.example.onlinestore.exception.IncorrectUsernameException;
import com.example.onlinestore.exception.NotFoundUserException;
import com.example.onlinestore.exception.PasswordChangeException;
import com.example.onlinestore.mapper.UserMapper;
import com.example.onlinestore.model.ImageModel;
import com.example.onlinestore.model.UserModel;
import com.example.onlinestore.repository.ImageRepository;
import com.example.onlinestore.repository.UserRepository;
import com.example.onlinestore.security.UserDetailsCustom;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;
@Service
@Primary
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, ImageRepository imageRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> optionalUser = userRepository.getUserByUsernameIgnoreCase(username);

        if (optionalUser.isPresent()) {
            UserModel userModel = optionalUser.get();
            return new UserDetailsCustom(userModel);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    @Transactional
    public void createUser(RegisterReq registerReq) {
        if (userRepository.findByUsername(registerReq.getUsername()).isPresent()) {
            throw new IncorrectUsernameException();
        }
        UserModel userModel = new UserModel();
        userModel.setUsername(registerReq.getUsername());
        userModel.setPassword(passwordEncoder.encode(registerReq.getPassword()));
        userModel.setRole(Role.USER);
        userModel.setFirstName(registerReq.getFirstName());
        userModel.setLastName(registerReq.getLastName());
        userModel.setPhone(registerReq.getPhone());
        userRepository.save(userModel);
    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @Transactional
    public boolean setPassword(NewPassword newPassword,Authentication authentication) {
        UserModel currentUser = userRepository.findByUsername(authentication.getName()).orElseThrow(NotFoundUserException::new);
        if (passwordEncoder.matches(newPassword.getCurrentPassword(), currentUser.getPassword())) {
            currentUser.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public User getUser(Authentication authentication) {
        UserModel currentUser = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        User user = new User();
        userMapper.toUser(user, currentUser);
        return user;
    }

    @Transactional
    public boolean updateUser(User user) {
        Optional<UserModel> currentUser = userRepository.findByUsername(getCurrentUsername());
        currentUser.ifPresent((userModel) -> userMapper.toUserModel(userModel, user));
        return currentUser.isPresent();
    }
    @Transactional(readOnly = true)
    public ImageModel getUserImage(Integer userId) {
        return userRepository.findById(userId).map(UserModel::getImageModel).orElse(null);
    }

    @Transactional
    public void updateUserImage(MultipartFile file) throws IOException {
        UserModel user = userRepository.findByUsername(getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ImageModel image = imageRepository.findById(user.getId()).orElse(new ImageModel());
//        image.setFileSize(file.getSize());
        image.setImage(file.getBytes());
        imageRepository.save(image);
        user.setImageModel((image));

    }
}
