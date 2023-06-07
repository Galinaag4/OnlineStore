package com.example.onlinestore.controller;

import com.example.onlinestore.dto.NewPassword;
import com.example.onlinestore.dto.User;
import com.example.onlinestore.mapper.UserMapper;
import com.example.onlinestore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private UserMapper userMapper;
    private UserRepository userRepository;

    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword) {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestPart MultipartFile image) {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }
}



