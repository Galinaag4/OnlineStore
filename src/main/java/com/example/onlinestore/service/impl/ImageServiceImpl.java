package com.example.onlinestore.service.impl;

import com.example.onlinestore.model.ImageModel;
import com.example.onlinestore.repository.AdsRepository;
import com.example.onlinestore.repository.ImageRepository;
import com.example.onlinestore.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final AdsRepository adsRepository;
    private final ImageRepository imageRepository;


//    @Override
//    public byte[] updateAdsImage(Integer id, MultipartFile image, Authentication authentication) throws IOException {
//        return new byte[0];
//    }
//
//    @Override
//    public byte[] getImage(String id) {
//        return new byte[0];
//    }
//
//    @Override
//    public byte[] getImage(int id) throws IOException {
//        return new byte[0];
//    }



     //Метод сохраняет картинку в БД
    @Override
    public ImageModel saveImage(MultipartFile image) {
        ImageModel imageModel = new ImageModel();
        try {
            {
                byte[] bytes = image.getBytes();
                imageModel.setImage(bytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageRepository.saveAndFlush(imageModel);
    }

    //Метод сохраняет и обновляет картинку в БД
    @Override
    public ImageModel updateImage(MultipartFile newImage, ImageModel oldImageModel) {
        try {
            byte[] bytes = newImage.getBytes();
            oldImageModel.setImage(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageRepository.saveAndFlush(oldImageModel);
    }
}
