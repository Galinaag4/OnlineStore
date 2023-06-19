package com.example.onlinestore.service.impl;

import com.example.onlinestore.dto.Ads;
import com.example.onlinestore.exception.AdsNotFoundException;
import com.example.onlinestore.model.AdsModel;
import com.example.onlinestore.model.ImageModel;
import com.example.onlinestore.repository.AdsRepository;
import com.example.onlinestore.repository.ImageRepository;
import com.example.onlinestore.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final AdsRepository adsRepository;

    private final ImageRepository imageRepository;


    @Override
    public byte[] updateAdsImage(Integer id, MultipartFile image, Authentication authentication) throws IOException {
        return new byte[0];
    }

    @Override
    public byte[] getImage(int id) throws IOException {
        return new byte[0];
    }
}
