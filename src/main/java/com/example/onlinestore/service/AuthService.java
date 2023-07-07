package com.example.onlinestore.service;

import com.example.onlinestore.dto.NewPassword;
import com.example.onlinestore.dto.RegisterReq;
import com.example.onlinestore.dto.Role;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq);

}
