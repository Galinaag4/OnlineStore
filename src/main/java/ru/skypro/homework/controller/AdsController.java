package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {
    @GetMapping()
    public ResponseEntity<?> getAds() {
        return getAds();
    }

    @PostMapping()
    public ResponseEntity<?> addAds() {
        return null;
    }

    @GetMapping("/{ad_pk}/comments")
    public ResponseEntity<?> getComments() {
        return getComments();
    }

    @PostMapping("/{ad_pk}/comments")

    public ResponseEntity<?> addComments() {
        return addComments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFullAd() {
        return getFullAd();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAds() {
        return removeAds();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAds() {
        return updateAds();
    }

    @GetMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<?> getComment() {
        return getComment();
    }

    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<Void> deleteComments() {
        return deleteComments();

    }
    @PatchMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<?> updateComments(){
        return updateComments();
    }
    @GetMapping("/{me}")
    public ResponseEntity<?> getAdsMe(){
        return getAdsMe();
    }
}
