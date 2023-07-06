package com.example.onlinestore.service.impl;

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
import com.example.onlinestore.repository.ImageRepository;
import com.example.onlinestore.repository.UserRepository;
import com.example.onlinestore.service.AdsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

//Класс-сервис, который содержит реализацию интерфейса AdsService
@Slf4j
@Service
@Transactional
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
    private final AdsMapper adsMapper;
    private final ImageRepository imageRepository;

    private final ImageServiceImpl imageService;
    private final UserRepository userRepository;
    private final UserServiceImpl userServiceImpl;
    private final CommentServiceImpl commentServiceImpl;

    public AdsServiceImpl(AdsRepository adsRepository, ImageRepository imageRepository,
                          UserRepository userRepository, UserServiceImpl userServiceImpl,
                          AdsMapper adsMapper, CommentServiceImpl commentServiceImpl,
                          ImageServiceImpl imageService) {
        this.adsRepository = adsRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.userServiceImpl = userServiceImpl;
        this.adsMapper = adsMapper;
        this.commentServiceImpl = commentServiceImpl;
        this.imageService = imageService;
    }

//    public ResponseWrapperAds getAllAds() {
//        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
//        List<AdsModel> adsList = adsRepository.findAll();
//        responseWrapperAds.setResults(adsMapper.adListToAdsDtoList(adsList));
//        responseWrapperAds.setCount(adsList.size());
//        return responseWrapperAds;
//    }


    //Метод создает объявление
    @Override
    public Ads createAds(CreateAds ads, MultipartFile image, Authentication authentication) {
        UserModel userModel = userServiceImpl.getUserModel(authentication);
        AdsModel adsModel = adsMapper.toAdsModel(ads);
        ImageModel imageModel = imageService.saveImage(image);
        adsModel.setUserModel(userModel);
        adsModel.setImageModel(imageModel);
        adsRepository.save(adsModel);
        Ads ad = adsMapper.adsModelToAds(adsModel);
        ad.setImage(String.format("/ads/%s/image", adsModel.getImageModel().getId()));
        return ad;
    }

    @Override
    public CreateAds editAds(Integer id, Ads ads, Authentication authentication) {
        return null;
    }

    @Override
    public void deleteAds(Integer adsId, Authentication authentication) {

    }

    //Метод возвращает все объявления
    public ResponseWrapperAds getAllAds(String title) {
        List <AdsModel> adsModelList;
        if (title == null) {
            adsModelList = adsRepository.findAll();
        } else {
            adsModelList = adsRepository.findByTitleContainingIgnoreCaseOrderByTitle(title);
        }
        List<Ads> adsList = insertLinkToAdsToList(adsModelList);
        return createResponseWrapperAds(adsList.size(), adsList);
    }

    //Метод возвращает список объявлений авторизованного пользователя
    @Override
    public ResponseWrapperAds getAllAdsUser(Authentication authentication) {
        Integer userId = userServiceImpl.getUserModel(authentication).getId();
        List<AdsModel> adsModelList = adsRepository.findAllByUserModelId(userId);
        List<Ads> adsList = insertLinkToAdsToList(adsModelList);
        return createResponseWrapperAds(adsList.size(), adsList);
    }

    //Метод ищет объявление по id и возвращает полную информацию о нем
    @Override
    public FullAds getFullAdsById(Integer adsId) {
        AdsModel adsModel = adsRepository.findById(adsId).orElseThrow(AdsNotFoundException::new);
        FullAds fullAds = adsMapper.toFullAdsDto(adsModel);
        fullAds.setImage(String.format("ads/%s/image", adsModel.getImageModel().getId()));
        return fullAds;
    }

    @Override
    public AdsModel getAdsModel(Integer adsId) {
        return null;
    }

    //Метод меняет картинку объявления
    @Override
    public String updateAdsImage(Integer id, MultipartFile image) throws IOException {
        AdsModel adsModel = getAdsModel(id);
        ImageModel oldImageModel = adsModel.getImageModel();
        if (oldImageModel == null) {
            ImageModel newImageModel = imageService.saveImage(image);
            adsModel.setImageModel(newImageModel);
            adsRepository.saveAndFlush(adsModel);
            return "Картинка успешно загружена";
        } else {
            ImageModel updateImage = imageService.updateImage(image, oldImageModel);
            adsModel.setImageModel(updateImage);
            adsRepository.saveAndFlush(adsModel);
            return "Картинка успешно загружена";
        }
    }

    @Transactional(readOnly = true)
    public ImageModel getAdImage(Integer adsId) {
        return adsRepository.findById(adsId).map(AdsModel::getImageModel).orElse(null);
    }



    //Метод создает объявление
//    @Transactional
//    public Ads addAd(CreateAds properties, MultipartFile image, Authentication authentication) throws IOException {
//        UserModel userModel = userServiceImpl.getUserModel(authentication);
//        AdsModel adsModel = adsMapper.toAdsModel(properties);
//        ImageModel imageModel = imageService.saveImage(image);
//        adsModel.setUserModel(userModel);
//        adsModel.setImageModel(imageModel);
//        adsRepository.save(adsModel);
//        Ads ads = adsMapper.adsModelToAds(adsModel);
//        ads.setImage(String.format("ads/%s/image", adsModel.getImageModel().getId()));
//        return ads;
//        ImageModel imageModel = new ImageModel();
//        imageModel.setImage(image.getBytes());
//        imageRepository.save(imageModel);
//        adsModel.setImageModel(imageModel);
//        adsModel.setUserModel(userRepository.findByUsername(userServiceImpl.getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found")));
//        adsRepository.save(adsModel);
//        return adsMapper.adsModelToAds(adsModel);


    @Transactional
    public FullAds getAds(Integer id) {
        return adsRepository.findById(id).map(adsMapper::toFullAdsDto).orElse(null);
    }

//    @Transactional
//    public ResponseWrapperAds getAdsMe() {
//        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
//        UserModel userModel = userRepository.findByUsername(userServiceImpl.getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        List<AdsModel> adList = adsRepository.findAllByUserModelId(userModel.getId());
//        responseWrapperAds.setResults(adsMapper.adListToAdsDtoList(adList));
//        responseWrapperAds.setCount(adList.size());
//        return responseWrapperAds;
//    }


    @Transactional
    public void removeAd(Integer id, Authentication authentication) {
        imageRepository.delete(adsRepository.findById(id).map(AdsModel::getImageModel).orElseThrow());
        commentServiceImpl.deleteCommentsByAdId(id);
        adsRepository.deleteById(id);
    }

//    @Transactional
//    public void updateAdImage(Integer id, MultipartFile file) throws IOException {
//        AdsModel adsModel = adsRepository.findById(id).orElseThrow();
//        ImageModel imageModel = imageRepository.findById(adsModel.getId()).orElse(new ImageModel());
////        imageModel.setFileSize(file.getSize());
//        imageModel.setImage(file.getBytes());
//        imageRepository.save(imageModel);
//        adsModel.setImageModel(imageModel);
//    }

    @Transactional
    public Ads updateDto (Integer id, CreateAds properties) {
        AdsModel adsModel = adsRepository.findById(id).orElseThrow();
        adsModel.setTitle(properties.getTitle());
        adsModel.setDescription(properties.getDescription());
        adsModel.setPrice(properties.getPrice());

        adsRepository.save(adsModel);

        return adsMapper.adsModelToAds(adsModel);
    }

    //Метод переносит данные из List AdsModel в List DTO
    private List<Ads> insertLinkToAdsToList(List <AdsModel> adsModelList) {
        List<Ads> adsList = adsMapper.adListToAdsDtoList(adsModelList);
        for (int i = 0; i < adsList.size(); i++) {
            String imageList = String.format("/ads/%s/image", adsModelList.get(i).getImageModel().getId());
            adsList.get(i).setImage(imageList);
        }
        return adsList;
    }

    //Метод переносит данные из List<Ads> в ResponseWrapperAds
    private ResponseWrapperAds createResponseWrapperAds(Integer count, List<Ads> adsList) {
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        responseWrapperAds.setCount(count);
        responseWrapperAds.setResults(adsList);
        return responseWrapperAds;
    }
}
