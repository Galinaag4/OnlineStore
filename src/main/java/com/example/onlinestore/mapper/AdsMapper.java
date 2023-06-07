package com.example.onlinestore.mapper;

import com.example.onlinestore.dto.Ads;
import com.example.onlinestore.model.AdsModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdsMapper {
    AdsMapper INSTANCE = Mappers.getMapper( AdsMapper.class );
    Ads adsToDto (AdsModel adsModel);
    AdsModel adsToModel(Ads ads);
}
