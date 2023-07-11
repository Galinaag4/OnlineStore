package com.example.onlinestore.service;

import com.example.onlinestore.dto.Ads;
import com.example.onlinestore.dto.CreateAds;
import com.example.onlinestore.dto.FullAds;
import com.example.onlinestore.dto.ResponseWrapperAds;
import com.example.onlinestore.exception.AdsNotFoundException;
import com.example.onlinestore.mapper.AdsMapper;
import com.example.onlinestore.model.AdsModel;
import com.example.onlinestore.model.ImageModel;
import com.example.onlinestore.model.UserModel;
import com.example.onlinestore.repository.AdsRepository;
import com.example.onlinestore.repository.CommentRepository;
import com.example.onlinestore.repository.ImageRepository;
import com.example.onlinestore.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Класс - сервис для работы с объявлениями
 *
 * @see AdsService
 * @see AdsRepository
 */

@Slf4j
@Service
public class AdsService {

    private final AdsRepository adsRepository;
    private final ImageRepository imageRepository;

    private final ImageService imageService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final AdsMapper adsMapper;
    private final PropertyService propertyService;
    private final CommentRepository commentRepository;


    public AdsService(AdsRepository adsRepository, ImageRepository imageRepository,
                      UserRepository userRepository, UserService userService, AdsMapper adsMapper,
                      PropertyService propertyService, ImageService imageService, CommentRepository commentRepository) {
        this.adsRepository = adsRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.adsMapper = adsMapper;
        this.propertyService = propertyService;
        this.imageService = imageService;
        this.commentRepository = commentRepository;
    }

    /**
     * Метод возвращает все объявления
     * {@link AdsRepository#findAll()}
     *
     * @return  {@link ResponseWrapperAds}
     */
    public ResponseWrapperAds getAllAds() {
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        List<AdsModel> adsList = adsRepository.findAll();
        responseWrapperAds.setResults(adsMapper.adListToAdsDtoList(adsList));
        responseWrapperAds.setCount(adsList.size());
        return responseWrapperAds;
    }


    /**
     * Метод возвращает картинку объявления по id
     *
     * @param adsId
     * {@link AdsRepository#findById(Object)}
     *
     * @return {@link ImageModel}
     */
    @Transactional(readOnly = true)
    public ImageModel getAdImage(Integer adsId) {
        AdsModel ads = adsRepository.findById(adsId).orElse(null);
        return ads.getImageModel();
    }

    /**
     * Метод создает объявление
     *
     * @param properties,file,authentication
     * {@link AdsMapper#toAdsModel(CreateAds)}
     * {@link ImageRepository#save(Object)}
     * {@link AdsRepository#save(Object)}
     *
     * @return  {@link Ads}
     */
    @Transactional
    public Ads addAd (CreateAds properties, MultipartFile file, Authentication authentication) throws IOException {
        AdsModel adsModel = adsMapper.toAdsModel(properties);
        adsModel.setUserModel(userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found")));
        adsRepository.save(adsModel);
        ImageModel imageModel = new ImageModel();
        imageModel.setImage(file.getBytes());
        imageModel.setAdsModel(adsModel);
        imageRepository.save(imageModel);

        return adsMapper.adsModelToAds(adsModel);
    }

    /**
     * Метод возвращает все объявления по id
     *
     * @param id
     * {@link AdsRepository#findById(Object)} ()}
     *
     * @return  {@link FullAds}
     */
    @Transactional
    public FullAds getAds(Integer id) {
        return adsRepository.findById(id).map(adsMapper::toFullAdsDto).orElse(null);
    }


    /**
     * Метод ищет и возвращает список всех объявлений авторизированного пользователя
     * {@link UserRepository#findByUsername(String)}
     * {@link AdsRepository#findAllByUserModelId(Integer)}
     *
     * @return  {@link ResponseWrapperAds}
     */
    @Transactional
    public ResponseWrapperAds getAdsMe() {
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        UserModel userModel = userRepository.findByUsername(userService.getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<AdsModel> adList = adsRepository.findAllByUserModelId(userModel.getId());
        responseWrapperAds.setResults(adsMapper.adListToAdsDtoList(adList));
        responseWrapperAds.setCount(adList.size());
        return responseWrapperAds;
    }


    /**
     * Метод удаляет объявление по id
     *
     * @param email,id
     * {@link AdsRepository#deleteById(Object)}
     * @throws AdsNotFoundException если объявление не найдено
     */
    @Transactional
    public boolean removeAd(String email, int id) {
        AdsModel adsModel = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        UserModel userModel = adsModel.getUserModel();
        if (propertyService.isThisUserOrAdmin(email, userModel)) {
            commentRepository.deleteAllByAdsModel_Id(id);
            adsRepository.deleteAllById(id);
            return true;
        }
        return false;
    }




    /**
     * Метод меняет картинку объявления и сохраняет изменения в БД
     *
     * @param id,file
     *
     * {@link AdsRepository#findById(Object)}
     * {@link ImageRepository#findById(Object)}
     * {@link ImageRepository#save(Object)},
     */
    @Transactional
    public byte[] updateAdImage(Integer id, MultipartFile file) throws IOException {

//        imageRepository.deleteImageModelsByAdsModel_Id(id);
        AdsModel adsModel = adsRepository.findById(id).orElseThrow();
        ImageModel imageToSave = adsModel.getImageModel();
imageToSave.setImage(file.getBytes());

        imageToSave.setAdsModel(adsModel);

        imageRepository.save(imageToSave);
        return imageToSave.getImage();

//        AdsModel adsModel = adsRepository.findById(id).orElseThrow();
//        ImageModel imageModel = imageRepository.findById(adsModel.getId()).orElse(new ImageModel());
//        imageModel.setImage(file.getBytes());
//        imageRepository.save(imageModel);
//        adsModel.setImageModel(imageModel);


    }

    @Transactional
    public Ads updateDto(Integer id, CreateAds properties, String username) {
        Optional<AdsModel> optionalAdsModel = adsRepository.findById(id);
        if (optionalAdsModel.isPresent()) {
            AdsModel adsModel = optionalAdsModel.get();
            UserModel userModel = adsModel.getUserModel();
            if (propertyService.isThisUserOrAdmin(username, userModel)) {
                adsMapper.toAdsModel(properties);
                adsRepository.save(adsModel);
                return adsMapper.adsModelToAds(adsModel);
            }
        }
        return null;
    }

}
