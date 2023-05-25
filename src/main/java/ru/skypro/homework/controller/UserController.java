package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateUser() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }
}



