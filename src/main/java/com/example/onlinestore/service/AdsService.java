package com.example.onlinestore.service;

import com.example.onlinestore.dto.*;
import com.example.onlinestore.model.AdsModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@Service
public interface AdsService {
    Ads createAds (CreateAds ads, MultipartFile image, Authentication authentication);

    CreateAds editAds(Integer id, Ads ads, Authentication authentication);
    void deleteAds (Integer adsId, Authentication authentication);
    ResponseWrapperAds getAllAds(String title);

    ResponseWrapperAds getAllAdsUser(Authentication authentication);
    FullAds getFullAdsById (Integer adsId);

    AdsModel getAdsModel(Integer adsId);

    String updateAdsImage(Integer id, MultipartFile image) throws IOException;






}
