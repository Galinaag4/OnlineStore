package com.example.onlinestore.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class ResponseWrapperComment<C> {
    private final int count;
    private final Collection<C> results;

    public ResponseWrapperComment(Collection<C> results) {
        this.count = results.size();
        this.results = results;
    }
}
