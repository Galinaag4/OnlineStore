package com.example.onlinestore.service;


import com.example.onlinestore.dto.Role;
import com.example.onlinestore.exception.NotFoundUserException;
import com.example.onlinestore.model.UserModel;
import com.example.onlinestore.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class PropertyService {

    private final UserRepository userRepository;

    public PropertyService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isThisUser(String username, UserModel userModel) {
        if (username.equals(userModel.getUsername())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isAdmin(String username) {
        UserModel userModel = userRepository.findByUsername(username).orElseThrow(NotFoundUserException::new);
        if (userModel.getRole() == (Role.ADMIN)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isThisUserOrAdmin(String username, UserModel userModel) {
        return isThisUser(username, userModel) || isAdmin(username);
    }
}
