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
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdsService {

    private final AdsRepository adsRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final AdsMapper adsMapper;
    private final CommentService commentService;
    private final PropertyService propertyService;

    public AdsService(AdsRepository adsRepository, ImageRepository imageRepository, ImageService imageService, UserRepository userRepository, UserService userService, AdsMapper adsMapper, CommentService commentService, PropertyService propertyService) {
        this.adsRepository = adsRepository;
        this.imageRepository = imageRepository;
        this.imageService = imageService;
        this.userRepository = userRepository;
        this.userService = userService;
        this.adsMapper = adsMapper;
        this.commentService = commentService;
        this.propertyService = propertyService;
    }

    public ResponseWrapperAds getAllAds() {
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        List<AdsModel> adsList = adsRepository.findAll();
        responseWrapperAds.setResults(adsMapper.adListToAdsDtoList(adsList));
        responseWrapperAds.setCount(adsList.size());
        return responseWrapperAds;
    }

    @Transactional(readOnly = true)
    public ImageModel getAdImage(Integer adsId) {
        return adsRepository.findById(adsId).map(AdsModel::getImageModel).orElse(null);
    }

    @Transactional
    public Ads addAd(CreateAds properties, MultipartFile file,Authentication authentication) throws IOException {
        AdsModel adsModel = adsMapper.toAdsModel(properties);
        ImageModel imageModel = new ImageModel();
//        imageModel.setFileSize(file.getSize());
        imageModel.setImage(file.getBytes());
        imageRepository.save(imageModel);
        adsModel.setImageModel(imageModel);
        adsModel.setUserModel(userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found")));
        adsRepository.save(adsModel);
        return adsMapper.adsModelToAds(adsModel);
    }
//    public Ads addAd(CreateAds properties, ImageModel image) {
//        AdsModel adsModel = adsMapper.toAdsModel(properties);
//        adsModel.setUserModel(userRepository.findByUsername(userService.getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found")));
//        ImageModel imageModel;
//        imageModel = imageService.save(image);
//        adsModel.setImageModel(imageModel);
//        adsRepository.saveAndFlush(adsModel);
//        return adsMapper.adsModelToAds(adsModel);
//    }

    @Transactional
    public FullAds getAds(Integer id) {
        return adsRepository.findById(id).map(adsMapper::toFullAdsDto).orElse(null);
    }

    @Transactional
    public ResponseWrapperAds getAdsMe() {
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        UserModel userModel = userRepository.findByUsername(userService.getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<AdsModel> adList = adsRepository.findAllByUserModelId(userModel.getId());
        responseWrapperAds.setResults(adsMapper.adListToAdsDtoList(adList));
        responseWrapperAds.setCount(adList.size());
        return responseWrapperAds;
    }

    @Transactional
    public boolean removeAd(String email, int id) {
        AdsModel adsModel = adsRepository.findById(id).orElseThrow(AdsNotFoundException::new);
        UserModel userModel = adsModel.getUserModel();
//        imageRepository.delete(adsRepository.findById(id).map(AdsModel::getImageModel).orElseThrow());
//        commentService.deleteCommentsByAdId(id);
        if (propertyService.isThisUserOrAdmin(email, userModel)) {
//            try {
//                Files.deleteIfExists(Path.of(ads.getImage().getFilePath()));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
            adsRepository.deleteById(id);
            return true;
        }
        return false;
    }
//    public void removeAd(Integer id) {
//        imageRepository.delete(adsRepository.findById(id).map(AdsModel::getImageModel).orElseThrow());
//        commentService.deleteCommentsByAdId(id);
//        adsRepository.deleteById(id);
//    }

    @Transactional
    public void updateAdImage(Integer id, MultipartFile file) throws IOException {
        AdsModel adsModel = adsRepository.findById(id).orElseThrow();
        ImageModel imageModel = imageRepository.findById(adsModel.getId()).orElse(new ImageModel());
//        imageModel.setFileSize(file.getSize());
        imageModel.setImage(file.getBytes());
        imageRepository.save(imageModel);
        adsModel.setImageModel(imageModel);
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
