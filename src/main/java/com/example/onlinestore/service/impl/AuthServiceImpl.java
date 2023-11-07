package com.example.onlinestore.service.impl;

import com.example.onlinestore.dto.Role;
import com.example.onlinestore.mapper.UserMapper;
import com.example.onlinestore.model.UserModel;
import com.example.onlinestore.repository.UserRepository;
import com.example.onlinestore.service.AuthService;
import com.example.onlinestore.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.onlinestore.dto.RegisterReq;

/**
 * Класс - сервис, содержащий реализацию интерфейса {@link AuthService}
 *
 * @see UserService
 * @see PasswordEncoder
 * @see UserMapper
 * @see UserRepository
 */
@Log4j2
@Service
public class AuthServiceImpl implements AuthService {

  private final UserService userService;
  private final UserMapper userMapper;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthServiceImpl(UserService userService, UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.userMapper = userMapper;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Метод производит авторизацию пользователя в системе
   * @return {@link PasswordEncoder#matches(CharSequence, String)} )}
   */
  @Override
  public boolean login(String userName, String password) {
    UserDetails userDetails = userService.loadUserByUsername(userName);
    return passwordEncoder.matches(password, userDetails.getPassword());
  }

  /**
   * Метод регистрирует пользователя в системе
   *
   * {@link UserRepository#getUserByUsernameIgnoreCase (RegisterReq)}
   * {@link UserMapper#mapRegisterReqToUserModel(RegisterReq)} 
   * {@link UserRepository#save(Object)} 
   */
  @Override
  public boolean register(RegisterReq registerReq, Role role) {
//    userService.createUser(registerReq);
//    log.info("Зарегистрирован новый пользователь: " + registerReq.getUsername());
//    return true;
    if (userRepository.getUserByUsernameIgnoreCase(registerReq.getUsername()).isPresent()) {
      return false;
    } else {
      UserModel userModel = userMapper.mapRegisterReqToUserModel(registerReq);
      userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
      userRepository.save(userModel);
      log.info("Зарегистрирован новый пользователь: " + registerReq.getUsername());
      return true;
    }
  }
}
