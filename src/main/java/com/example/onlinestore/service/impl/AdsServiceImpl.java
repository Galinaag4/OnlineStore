package com.example.onlinestore.service.impl;

import com.example.onlinestore.dto.Ads;
import com.example.onlinestore.dto.CreateAds;
import com.example.onlinestore.dto.FullAds;
import com.example.onlinestore.exception.AdsNotFoundException;
import com.example.onlinestore.exception.NotFoundUserException;
import com.example.onlinestore.mapper.AdsMapper;
import com.example.onlinestore.model.AdsModel;
import com.example.onlinestore.repository.AdsRepository;
import com.example.onlinestore.repository.UserRepository;
import com.example.onlinestore.service.AdsService;
import com.example.onlinestore.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Collection;
import static org.springframework.util.ObjectUtils.isEmpty;
@Slf4j
@Service
@Transactional
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    public AdsServiceImpl(AdsRepository adsRepository, UserRepository userRepository, ImageServiceImpl imageService) {
        this.adsRepository = adsRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
    }

    @Override
    public Collection<Ads> getAllAds(String title) {
        Collection<AdsModel> adsModels;
        if (!isEmpty(title)) {
            adsModels = adsRepository.findByTitleContainsOrderByTitle(title);
        } else {
            adsModels = adsRepository.findAll();
        }

        return AdsMapper.INSTANCE.adsModelCollectionToAds(adsModels);

    }

    @Override
    public Ads save(CreateAds adsModel, Authentication authentication, MultipartFile image) throws IOException {

        AdsModel newAdsModel = AdsMapper.INSTANCE.createAdsModelToAdsModel(adsModel);
        newAdsModel.setAuthor(userRepository.findUserByEmail(authentication.getName()).orElseThrow(NotFoundUserException::new));
        log.info("Save ads: " + newAdsModel);
        adsRepository.save(newAdsModel);

        imageService.updateAdsImage(newAdsModel.getId(),image,authentication);
        log.info("Photo have been saved");

        return AdsMapper
                .INSTANCE
                .adsModelToAds(newAdsModel);
    }

    @Override
    public void deleteAds(Integer adsId, Authentication authentication) {
        adsRepository.deleteAllById(adsId);
    }

    @Override
    public FullAds getFullAdsModel(Long adsId) {
        AdsModel adsModel = adsRepository.findById(adsId).orElseThrow(AdsNotFoundException::new);
        return AdsMapper.INSTANCE.toFullAds(adsModel);
    }


    @Override
    public Collection<Ads> getAdsModelByUser(String email) {
        int authorId = userRepository.getUserModelId(email);
        Collection<AdsModel> adsModels = adsRepository.findByAuthorId(authorId);
        return AdsMapper.INSTANCE.adsModelCollectionToAds(adsModels);
    }

}
