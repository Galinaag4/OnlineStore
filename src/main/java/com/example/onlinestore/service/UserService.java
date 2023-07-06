package com.example.onlinestore.service;

import com.example.onlinestore.dto.NewPassword;
import com.example.onlinestore.dto.User;
import com.example.onlinestore.exception.NotFoundUserNameException;
import com.example.onlinestore.exception.PasswordChangeException;
import com.example.onlinestore.model.UserModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
public interface UserService {

    User update(User user, Authentication authentication);

    User getUser (Authentication authentication);

    String updateAvatar(MultipartFile image, Authentication authentication) throws IOException;

    UserModel getUserModel(Authentication authentication);

    NewPassword setPassword(NewPassword newPassword, Authentication authentication) throws PasswordChangeException;

}
