package com.example.onlinestore.security;

import com.example.onlinestore.model.UserModel;
import com.example.onlinestore.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;

@Service
public class UserManagerSecurity  implements UserDetailsService {
    private final UserRepository userRepository;

    public UserManagerSecurity(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void createUser(UserDetails user) {

    }


    public void updateUser(UserDetails user) {

    }


    public void deleteUser(String username) {

    }


    public void changePassword(String oldPassword, String newPassword) {

    }


    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUsername(username).orElseThrow();
        return new UserSecurity(userModel);
    }
}
