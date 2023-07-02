package com.example.onlinestore.service.impl;

import com.example.onlinestore.dto.NewPassword;
import com.example.onlinestore.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import com.example.onlinestore.dto.RegisterReq;
import com.example.onlinestore.dto.Role;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserDetailsManager manager;

  private final UserServiceImpl service;

  private final PasswordEncoder encoder;

  public AuthServiceImpl(UserDetailsManager manager, UserServiceImpl service, PasswordEncoder passwordEncoder) {
    this.manager = manager;
    this.service = service;
    this.encoder = passwordEncoder;
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

    service.createUser(registerReq, role);
    return true;

  }

  @Override
  public boolean changePassword(NewPassword newPassword, String name) {
    return false;
  }
}
