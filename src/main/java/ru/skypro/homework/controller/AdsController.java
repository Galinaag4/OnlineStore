package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {
    @GetMapping()
    public ResponseEntity<?> getAds() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @PostMapping()
    public ResponseEntity<?> addAds() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @GetMapping("/{ad_pk}/comments")
    public ResponseEntity<?> getComments() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @PostMapping("/{ad_pk}/comments")

    public ResponseEntity<?> addComments() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFullAd() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAds() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAds() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @GetMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<?> getComment() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<Void> deleteComments() {
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();

    }
    @PatchMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<?> updateComments(){
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }
    @GetMapping("/{me}")
    public ResponseEntity<?> getAdsMe(){
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }
}
