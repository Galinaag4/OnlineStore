package com.example.onlinestore.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class ResponseWrapperAds {
    private final int count;
    private final Collection <Ads> results;

    public ResponseWrapperAds( Collection<Ads> results) {
        this.count = results.size();
        this.results = results;
    }
}
