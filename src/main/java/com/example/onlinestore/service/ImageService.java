package com.example.onlinestore.service;

import com.example.onlinestore.model.ImageModel;
import com.example.onlinestore.repository.AdsRepository;
import com.example.onlinestore.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Класс - сервис для работы с картинками
 *
 * @see ImageRepository
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
//    @Value("${ads.image.dir.path}")
//    private String imageDir;

    private final ImageRepository imageRepository;


    /**
     * Метод сохраняет картинку в базу данных
     *
     * {@link ImageRepository#saveAndFlush(Object)}
     *
     * @return {@link ImageModel}
     */
    public ImageModel save(MultipartFile image) {
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

    @Transactional(readOnly = true)
    public ImageModel read(int id) {
        return imageRepository.findById(id)
                .orElse(null);
    }


//    public Image saveImageFile(MultipartFile imageFile) throws IOException {
//        Path filePath = createPathFromFile(imageFile);
//        Files.createDirectories(filePath.getParent());
//        Files.deleteIfExists(filePath);
//        try (InputStream is = imageFile.getInputStream();
//             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
//             BufferedInputStream bis = new BufferedInputStream(is, 1024);
//             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)) {
//            bis.transferTo(bos);
//        }
//        return saveImageDetails(imageFile, filePath);
//    }

//    private Path createPathFromFile(MultipartFile imageFile) {
//        Path path = Path.of(String.format("%s/%s", imageDir, imageFile.getOriginalFilename()));
//        if (Files.exists(path)) {
//            path = Path.of(String.format("%s/%s.%s", imageDir, generateFileName(),
//                    getExtension(Objects.requireNonNull(imageFile.getOriginalFilename(), ""))));
//        }
//        return path;
//    }
//
//    private String generateFileName() {
//        int length = 8;
//        boolean useLetters = true;
//        boolean useNumbers = true;
//        return RandomStringUtils.random(length, useLetters, useNumbers);
//    }

    /**
     * Получение расширения файла изображения
     *
     * @param fileName полное имя файла
     * @return расширение файла
     */
//    private String getExtension(String fileName) {
//        return fileName.substring(fileName.lastIndexOf(".") + 1);
//    }

//    private Image saveImageDetails(MultipartFile imageFile, Path filePath) {
//        Image imageDetails = imageRepository.findByFilePath(filePath.toString()).orElse(new Image());
//        imageDetails.setFilePath(filePath.toString());
//        imageDetails.setFileExtension(getExtension(Objects.requireNonNull(imageFile.getOriginalFilename())));
//        imageDetails.se(imageFile.getSize());
//        imageDetails.setMediaType(imageFile.getContentType());
//        return imageRepository.saveAndFlush(imageDetails);
//    }

    //    /**
//     * Получение изображения с диска
//     *
//     * @param imageId  идентификатор сущности изображения
//     * @param response ответ сервера
//     * @throws {@link IOException}
//     */
//    public void transferImageToResponse(Integer imageId, HttpServletResponse response) throws IOException {
//        Image imageDetails = imageRepository.findById(imageId).orElseThrow(ImageNotFountException::new);
//        try (InputStream is = Files.newInputStream(imageDetails.getPath());
//             OutputStream os = response.getOutputStream()) {
//            response.setStatus(200);
//            response.setContentType(imageDetails.getMediaType());
//            response.setContentLength((int) imageDetails.getFileSize());
//            is.transferTo(os);
//        }
//    }
}


