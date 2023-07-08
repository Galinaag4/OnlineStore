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

/**
 * Класс - сервис для работы с пользователем и его данными, содержащий реализацию интерфейса {@link UserDetailsService}
 */
@Service
@Primary
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, ImageRepository imageRepository,
                       ImageService imageService, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.imageRepository = imageRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    /**
     * Метод находит пользователя и возвращает его данные
     *
     *
     * @param username
     *
     * {@link UserRepository#getUserByUsernameIgnoreCase(String)}
     * @throws UsernameNotFoundException если пользователь не найден
     *
     * @return {@link UserDetails}
     */
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


    /**
     * Метод создает нового пользователя
     *
     * @param registerReq
     *
     * {@link UserRepository#findByUsername(String)}
     * {@link PasswordEncoder#encode(CharSequence)}
     * {@link UserRepository#save(Object)}
     */
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


    /**
     * Метод позволяет получить текущее имя пользователя
     *
     * @return {@link String}
     */
    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }


    /**
     * Метод меняет пароль
     *
     * @param newPassword,authentication
     *
     * {@link UserRepository#findByUsername(String)}
     * {@link PasswordEncoder#matches(CharSequence, String)}
     * {@link PasswordEncoder#encode(CharSequence)}
     * @throws NotFoundUserException если пользователь не найден
     */
    @Transactional
    public boolean setPassword(NewPassword newPassword,Authentication authentication) {
        UserModel currentUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(NotFoundUserException::new);
        if (passwordEncoder.matches(newPassword.getCurrentPassword(), currentUser.getPassword())) {
            currentUser.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
            return true;
        }
        return false;
    }


    /**
     * Метод позволяет получить пользователя по имени
     *
     * @param authentication
     *
     * {@link UserRepository#findByUsername(String)}
     * {@link UserMapper#toUser(User, UserModel)}
     * @throws UsernameNotFoundException если пользователь не найден
     *
     * @return {@link User}
     */
    @Transactional(readOnly = true)
    public User getUser(Authentication authentication) {
        UserModel currentUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        User user = new User();
        userMapper.toUser(user, currentUser);
        return user;
    }

    /**
     * Метод позволяет изменить имя пользователя
     *
     * @param user
     *
     * {@link UserRepository#findByUsername(String)}
     * {@link UserMapper#toUserModel(UserModel, User)}
     */
    @Transactional
    public boolean updateUser(User user) {
        Optional<UserModel> currentUser = userRepository.findByUsername(getCurrentUsername());
        currentUser.ifPresent((userModel) -> userMapper.toUserModel(userModel, user));
        return currentUser.isPresent();
    }


    /**
     * Метод позволяет получить аватар пользователя по его id
     *
     * @param userId
     *
     * {@link UserRepository#findById(Object)}
     * @return {@link ImageModel}
     */
    @Transactional(readOnly = true)
    public ImageModel getUserImage(Integer userId) {
        return userRepository.findById(userId).map(UserModel::getImageModel).orElse(null);
    }

    /**
     * Метод позволяет обновить аватар пользователя
     *
     * @param file
     *
     * {@link UserRepository#findByUsername(String)}
     * {@link ImageRepository#findById(Object)}
     * {@link ImageRepository#save(Object)}
     * @throws UsernameNotFoundException если пользователь не найден по имени
     */
    @Transactional
    public void updateUserImage(MultipartFile file) throws UsernameNotFoundException, IOException {
        UserModel user = userRepository.findByUsername(getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ImageModel image = imageRepository.findById(user.getId()).orElse(new ImageModel());
        image.setImage(file.getBytes());
        imageRepository.save(image);
        user.setImageModel((image));
    }

    /**
     * Метод достает сущность UserModel из базы данных
     *
     * @param authentication
     * {@link UserRepository#findByUsername(String)}
     * @throws NotFoundUserException если пользователь не найден
     *
     * @return {@link UserModel}
     */
    public UserModel getUserModel(Authentication authentication) throws NotFoundUserException {
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(NotFoundUserException::new);
    }
}
