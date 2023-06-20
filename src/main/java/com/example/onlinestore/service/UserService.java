package com.example.onlinestore.service;

import com.example.onlinestore.dto.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
public interface UserService {
    User update(User user, String email) throws IOException;

    User getUser(String email) throws IOException;

    void updateAvatar(MultipartFile image, String name) throws IOException;

    byte[] getImage( int id) throws IOException;
}
