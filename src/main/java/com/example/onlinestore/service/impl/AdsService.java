package com.example.onlinestore.service.impl;

import com.example.onlinestore.dto.Ads;
import com.example.onlinestore.dto.CreateAds;
import com.example.onlinestore.dto.FullAds;
import com.example.onlinestore.dto.ResponseWrapperAds;
import com.example.onlinestore.mapper.AdsMapper;
import com.example.onlinestore.model.AdsModel;
import com.example.onlinestore.model.ImageModel;
import com.example.onlinestore.model.UserModel;
import com.example.onlinestore.repository.AdsRepository;
import com.example.onlinestore.repository.ImageRepository;
import com.example.onlinestore.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
@Slf4j
@Service
@Transactional
public class AdsService {

    private final AdsRepository adsRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final AdsMapper adsMapper;
    private final CommentService commentService;

    public AdsService(AdsRepository adsRepository, ImageRepository imageRepository, UserRepository userRepository, UserService userService, AdsMapper adsMapper, CommentService commentService) {
        this.adsRepository = adsRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.adsMapper = adsMapper;
        this.commentService = commentService;
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
    public Ads addAd(CreateAds properties, MultipartFile file) throws IOException {
        AdsModel adsModel = adsMapper.toAdsModel(properties);
        ImageModel imageModel = new ImageModel();
//        imageModel.setFileSize(file.getSize());
        imageModel.setImage(file.getBytes());
        imageRepository.save(imageModel);
        adsModel.setImageModel(imageModel);
        adsModel.setUserModel(userRepository.findByUsername(userService.getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found")));
        adsRepository.save(adsModel);
        return adsMapper.adsModelToAds(adsModel);
    }

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
    public void removeAd(Integer id) {
        imageRepository.delete(adsRepository.findById(id).map(AdsModel::getImageModel).orElseThrow());
        commentService.deleteCommentsByAdId(id);
        adsRepository.deleteById(id);
    }

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
    public Ads updateDto (Integer id, CreateAds properties) {
        AdsModel adsModel = adsRepository.findById(id).orElseThrow();
        adsModel.setTitle(properties.getTitle());
        adsModel.setDescription(properties.getDescription());
        adsModel.setPrice(properties.getPrice());

        adsRepository.save(adsModel);

        return adsMapper.adsModelToAds(adsModel);
    }
}
