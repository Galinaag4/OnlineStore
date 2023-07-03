package com.example.onlinestore.dto;

import com.example.onlinestore.model.AdsModel;
import lombok.Data;

import java.util.Collection;

@Data
public class ResponseWrapperAds {
    private  int count;
    private  Collection <Ads> results;

    public ResponseWrapperAds() {
    }
}
