package com.example.onlinestore.service;

import com.example.onlinestore.dto.RegisterReq;
import com.example.onlinestore.dto.Role;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
}