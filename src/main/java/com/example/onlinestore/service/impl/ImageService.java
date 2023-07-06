package com.example.onlinestore.service.impl;

import com.example.onlinestore.repository.AdsRepository;
import com.example.onlinestore.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final AdsRepository adsRepository;

    private final ImageRepository imageRepository;

}
