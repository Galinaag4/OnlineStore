package com.example.onlinestore.service;

import com.example.onlinestore.model.ImageModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
@Service
public interface ImageService {

//    byte[] updateAdsImage(Integer id, MultipartFile image, Authentication authentication) throws IOException;
//
//    byte[] getImage (String id);
//
//    byte[] getImage(int id) throws IOException;

    ImageModel saveImage(MultipartFile image);

    ImageModel updateImage(MultipartFile newImage, ImageModel oldModel);

}
