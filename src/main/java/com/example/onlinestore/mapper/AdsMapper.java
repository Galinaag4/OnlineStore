package com.example.onlinestore.mapper;

import com.example.onlinestore.dto.Ads;
import com.example.onlinestore.dto.CreateAds;
import com.example.onlinestore.dto.FullAds;
import com.example.onlinestore.model.AdsModel;
import com.example.onlinestore.model.ImageModel;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdsMapper {



    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "image", expression="java(mappedImages(adsModel))")
    Ads adsModelToAds(AdsModel adsModel);


    @Mapping(target = "pk", source = "id")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "image", expression="java(mappedImages(ads))")
    FullAds toFullAds(AdsModel adsModel);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    AdsModel createAdsModelToAdsModel(CreateAds createAds);
    default String mappedImages(AdsModel adsModel) {
        ImageModel image= adsModel.getImage();
       /* if (image == null||image.isEmpty()) {
            return null;
        }*/
        return  "/ads/"+adsModel.getId()+"/getImage";
    }


    Collection<Ads> adsModelCollectionToAds(Collection<AdsModel> adsModelsCollection);

}
