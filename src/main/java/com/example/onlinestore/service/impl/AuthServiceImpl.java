package com.example.onlinestore.service.impl;

import com.example.onlinestore.dto.NewPassword;
import com.example.onlinestore.exception.NotFoundUserException;
import com.example.onlinestore.exception.PasswordChangeException;
import com.example.onlinestore.model.UserModel;
import com.example.onlinestore.repository.UserRepository;
import com.example.onlinestore.service.AuthService;
import com.example.onlinestore.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import com.example.onlinestore.dto.RegisterReq;
@Log4j2
@Service
public class AuthServiceImpl implements AuthService {


  private final UserRepository userRepository;
  private final UserService userService;

  private final PasswordEncoder passwordEncoder;

  public AuthServiceImpl( UserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public boolean login(String userName, String password) {

    UserDetails userDetails = userService.loadUserByUsername(userName);
    return passwordEncoder.matches(password, userDetails.getPassword());
  }

  @Override
  public boolean register(RegisterReq registerReq) {
    userService.createUser(registerReq);
    log.info("Зарегистрирован новый пользователь: " + registerReq.getUsername());
    return true;
  }
}
