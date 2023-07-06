package com.example.onlinestore.service.impl;

import com.example.onlinestore.mapper.UserMapper;
import com.example.onlinestore.model.UserModel;
import com.example.onlinestore.repository.UserRepository;
import com.example.onlinestore.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import com.example.onlinestore.dto.RegisterReq;
import com.example.onlinestore.dto.Role;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final UserServiceImpl userService;
  private final PasswordEncoder encoder;

  public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper,
                         UserServiceImpl userService, PasswordEncoder encoder) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.userService = userService;
    this.encoder = encoder;
  }

  //метод позволяет пользователю авторизоваться
  @Override
  public boolean login(String userName, String password) {
    UserDetails userDetails = userService.loadUserByUsername(userName);
    return encoder.matches(password, userDetails.getPassword());
  }

  //метод позволяет пользователю зарегистрироваться
  @Override
  public boolean register(RegisterReq registerReq, Role role) {
    if (userRepository.findByEmail(registerReq.getUsername()).isPresent()) {
      return false;
    }
    registerReq.setRole(role);
    UserModel userModel = userMapper.mapRegisterReqToUserModel(registerReq);
    userModel.setPassword(encoder.encode(userModel.getPassword()));
    userRepository.save(userModel);
    return true;

  }

}
