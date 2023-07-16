package com.example.onlinestore.service;

import com.example.onlinestore.exception.ImageNotFoundException;
import com.example.onlinestore.model.ImageModel;
import com.example.onlinestore.repository.AdsRepository;
import com.example.onlinestore.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Класс - сервис для работы с картинками
 *
 * @see ImageRepository
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {


    private final ImageRepository imageRepository;


    /**
     * Метод сохраняет картинку в базу данных
     *
     * {@link ImageRepository#saveAndFlush(Object)}
     *
     * @return {@link ImageModel}
     */
    public ImageModel save (MultipartFile image) {
        ImageModel newImageModel = new ImageModel();
        try {
            byte[] bytes = image.getBytes();
            newImageModel.setImage(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageRepository.saveAndFlush(newImageModel);
    }

    /**
     * Метод обновляет и сохраняет картинку в базу данных
     *
     * {@link ImageRepository#saveAndFlush(Object)}
     *
     * @return {@link ImageModel}
     */
    public ImageModel updateImage(MultipartFile newImage, ImageModel oldImageModel) {
        try {
            byte[] bytes = newImage.getBytes();
            oldImageModel.setImage(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageRepository.saveAndFlush(oldImageModel);
    }

    /**
     * Метод достает картинку из БД по ее id
     *
     * @param id
     * {@link ImageRepository#findById(Object)}
     *
     * @throws ImageNotFoundException если картинка не найдена
     * @return {@link Byte}
     */
    public byte[] getImageById(Integer id) {
        ImageModel imageModel = imageRepository.findById(id).orElseThrow(ImageNotFoundException::new);
        return imageModel.getImage();
    }

    /**
     * Метод достает Optional из БД по ее id
     *
     * @param id {@link ImageRepository#findById(Object)}
     * @return {@link Optional}
     */
    public Optional<ImageModel> findImageModelId(Integer id) {
        return imageRepository.findById(id);
    }

    /**
     * Метод удаляет картинку из БД
     *
     * @param imageModel
     *
     * {@link ImageRepository#delete(Object)}
     */
    public void deleteImage (ImageModel imageModel) {
        imageRepository.delete(imageModel);
    }


}


