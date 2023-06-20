package com.example.onlinestore.service.impl;

import com.example.onlinestore.dto.NewPassword;
import com.example.onlinestore.repository.UserRepository;
import com.example.onlinestore.service.AuthService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import com.example.onlinestore.dto.RegisterReq;
import com.example.onlinestore.dto.Role;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserDetailsManager manager;

  private final PasswordEncoder encoder;
  private final UserRepository userRepository;

  public AuthServiceImpl(UserDetailsManager manager, UserRepository userRepository) {
    this.manager = manager;
    this.userRepository = userRepository;
    this.encoder = new BCryptPasswordEncoder();
  }

  @Override
  public boolean login(String userName, String password) {
    if (!manager.userExists(userName)) {
      return false;
    }
    UserDetails userDetails = manager.loadUserByUsername(userName);
    return encoder.matches(password, userDetails.getPassword());
  }

  @Override
  public boolean register(RegisterReq registerReq, Role role) {
    if (manager.userExists(registerReq.getUsername())) {
      return false;
    }
    manager.createUser(
        User.builder()
            .passwordEncoder(this.encoder::encode)
            .password(registerReq.getPassword())
            .username(registerReq.getUsername())
            .roles(role.name())
            .build());
    return true;
  }

  @Override
  public boolean changePassword(NewPassword newPassword, String name) {
    if (login(name, newPassword.getCurrentPassword())) {
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      manager.changePassword(
              newPassword.getCurrentPassword(),
              "{bcrypt}" + encoder.encode(newPassword.getNewPassword()));
      return true;
    }
    return false;
  }
  }
