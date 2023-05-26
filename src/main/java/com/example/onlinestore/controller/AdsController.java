package com.example.onlinestore.controller;

import com.example.onlinestore.dto.CreateAds;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("ads")
@RequiredArgsConstructor
public class AdsController {
    @GetMapping()
    public ResponseEntity<?> getAllAds() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @PostMapping()
    public ResponseEntity<?> addAds(@RequestBody CreateAds createAds) {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdsId(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAds(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAdsId(@PathVariable Integer id, @RequestBody CreateAds createAds) {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @GetMapping("/{me}")
    public ResponseEntity<?> getAdsUser(/*Не Знаю!!!*/){
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @PatchMapping("/{id}/{image}")
    public ResponseEntity<?> updateImageUser(@PathVariable Integer id, @RequestParam String image){
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }
}
