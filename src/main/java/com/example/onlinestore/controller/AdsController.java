package com.example.onlinestore.controller;

import com.example.onlinestore.dto.CreateAds;
import com.example.onlinestore.mapper.AdsMapper;
import com.example.onlinestore.repository.AdsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("ads")
@RequiredArgsConstructor
public class AdsController {
    private AdsMapper adsMapper;
    private AdsRepository adsRepository;
    @GetMapping()
    public ResponseEntity<?> getAllAds() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addAds(@RequestPart CreateAds createAds, @RequestPart MultipartFile image) {
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
    public ResponseEntity<?> getAdsUser(){
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @PatchMapping(value = "/{id}/{image}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateImageUser(@PathVariable Integer id, @RequestPart MultipartFile image){
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }
}
