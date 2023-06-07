package com.example.onlinestore.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class ResponseWrapperComment {
    private final int count;
    private final Collection<Comment> results;

    public ResponseWrapperComment(Collection<Comment> results) {
        this.count = results.size();
        this.results = results;
    }
}
