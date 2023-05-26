package com.example.onlinestore.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("ads")
@RequiredArgsConstructor
public class CommentController {
    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getComments(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> addComments(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComments(@PathVariable Integer adId, @PathVariable Integer commentId) {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();}

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> updateComments(@PathVariable Integer adId, @PathVariable Integer commentId){
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }
}
