package com.example.onlinestore.service.impl;

import com.example.onlinestore.dto.NewPassword;
import com.example.onlinestore.dto.RegisterReq;
import com.example.onlinestore.dto.Role;
import com.example.onlinestore.dto.User;
import com.example.onlinestore.exception.NotFoundUserException;
import com.example.onlinestore.exception.PasswordChangeException;
import com.example.onlinestore.mapper.UserMapper;
import com.example.onlinestore.model.ImageModel;
import com.example.onlinestore.model.UserModel;
import com.example.onlinestore.repository.UserRepository;
import com.example.onlinestore.security.UserPrincipal;
import com.example.onlinestore.service.UserService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;



import java.io.IOException;
import java.util.Objects;
import java.util.Optional;



//Класс - сервис, который содержит реализацию интерфейсов
@Service
@Primary
public class UserServiceImpl implements UserService, UserDetailsService,UserDetailsManager {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageServiceImpl imageService;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           UserMapper userMapper, ImageServiceImpl imageService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.imageService = imageService;
    }

//    @Override
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return (UserDetails) userRepository.findByUsername(username).orElseThrow(() ->
//                new UsernameNotFoundException("User not found"));
//    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
        Optional<UserModel> optionalUserModel = userRepository.findByEmail(username);
        if (optionalUserModel.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new UserPrincipal(optionalUserModel.get());
    }

    @Transactional
    public void createUser(UserModel userModel) {
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userRepository.save(userModel);
    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

//    @Transactional
//    public boolean setPassword (NewPassword newPassword) {
//        UserModel currentUser = userRepository.findByUsername(getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        if (passwordEncoder.matches(newPassword.getCurrentPassword(), currentUser.getPassword())) {
//            currentUser.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
//            return true;
//        }
//        return false;
//    }

    @Transactional(readOnly = true)
    public User getUser() {
        UserModel currentUser = userRepository.findByUsername(getCurrentUsername()).orElseThrow(()
                -> new UsernameNotFoundException("User not found"));
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

//    @Transactional
//    public void updateUserImage(MultipartFile file) throws IOException {
//        UserModel user = userRepository.findByUsername(getCurrentUsername()).orElseThrow(()
//                -> new UsernameNotFoundException("User not found"));
//        ImageModel image = imageRepository.findById(user.getId()).orElse(new ImageModel());
////        image.setFileSize(file.getSize());
//        image.setImage(file.getBytes());
//        imageRepository.save(image);
//        user.setImageModel((image));
//    }

//    public void createUser(RegisterReq registerReq, Role role) {
//        UserModel userModel = userMapper.mapRegisterReqToUserModel(registerReq, new UserModel());
//        userModel.setPassword(passwordEncoder.encode(registerReq.getPassword()));
//        userModel.setRole(Objects.requireNonNullElse(role, Role.USER));
//        userRepository.save(userModel);
//    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {
    }


    @Override
    public void deleteUser(String username) {
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
    @Override
    public User update (User user, Authentication authentication) {
        return null;
    }
    @Override
    public User getUser(Authentication authentication) {
        return null;
    }


     //Метод меняет или устанавливает аватар пользователя, после того, как найдет его в БД

    @Override
    public String updateAvatar(MultipartFile image, Authentication authentication) throws IOException {
        UserModel userModel = getUserModel(authentication);
        ImageModel oldImageModel = userModel.getImageModel();
        if (oldImageModel == null) {
            ImageModel newImageModel = imageService.saveImage(image);
            userModel.setImageModel(newImageModel);
            userRepository.save(userModel);
            return "Изображение загружено";
        } else {
            ImageModel upImage = imageService.updateImage(image, oldImageModel);
            userModel.setImageModel(upImage);
            userRepository.save(userModel);
            return "Изображение загружено";
        }
    }

    @Override
    public UserModel getUserModel(Authentication authentication) {
        return null;
    }

    //Метод, который меняет пароль
    @Override
    public NewPassword setPassword(NewPassword newPassword, Authentication authentication) throws PasswordChangeException {
        UserModel userModel = userRepository.findByEmail(authentication.getName())
                .orElseThrow(NotFoundUserException::new);
        if (passwordEncoder.matches(userModel.getPassword(), newPassword.getCurrentPassword())) {
            userModel.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
            userRepository.save(userModel);
            NewPassword newP = new NewPassword();
            newP.setCurrentPassword(userModel.getPassword());
            newP.setNewPassword(newPassword.getNewPassword());
            return newP;
        } else {
            throw new PasswordChangeException();
        }
    }
}
