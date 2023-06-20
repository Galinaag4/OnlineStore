package com.example.onlinestore.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
@Service
public interface ImageService {

    byte[] updateAdsImage(Integer id, MultipartFile image, Authentication authentication) throws IOException;

    byte[] getImage(int id) throws IOException;

}
