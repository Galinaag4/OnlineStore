package com.example.onlinestore.mapper;

import com.example.onlinestore.dto.Ads;
import com.example.onlinestore.dto.CreateAds;
import com.example.onlinestore.dto.FullAds;
import com.example.onlinestore.model.AdsModel;
import com.example.onlinestore.model.ImageModel;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
@Component
@Mapper(componentModel = "spring")
public interface AdsMapper {

    AdsModel toAdsModel(CreateAds createAds);
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "userModel.id")
    @Mapping(target = "image", expression="java(getImageModel(adsModel))")
    Ads adsModelToAds (AdsModel adsModel);
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "userModel.firstName")
    @Mapping(target = "authorLastName", source = "userModel.lastName")
    @Mapping(target = "email", source = "userModel.username")
    @Mapping(target = "phone", source = "userModel.phone")
    @Mapping(target = "image", expression="java(getImageModel(adsModel))")
    FullAds toFullAdsDto(AdsModel adsModel);

    default String getImageModel(AdsModel adsModel) {

        if (adsModel.getImageModel() == null) {
            return null;
        }
        return "/ads/image/" + adsModel.getId() + "/from-db";
    }
    List<Ads> adListToAdsDtoList(List<AdsModel> adList);

    List<AdsModel> toModelList(Collection<Ads> adsCollection);

    CreateAds adsModelToCreateAds(AdsModel adsModel);



}
