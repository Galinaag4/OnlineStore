package com.example.onlinestore.service;

import com.example.onlinestore.dto.Ads;
import com.example.onlinestore.dto.Comment;
import com.example.onlinestore.dto.CreateAds;
import com.example.onlinestore.dto.FullAds;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@Service
public interface AdsService {
    Collection<Ads> getAllAds(String title);
    Ads save(CreateAds ads,Authentication authentication, MultipartFile photo) throws IOException;
    void deleteAds(Integer adsId, Authentication authentication);
    FullAds getFullAdsModel(Long adsId);
    Collection<Ads> getAdsModelByUser(String email);

}
