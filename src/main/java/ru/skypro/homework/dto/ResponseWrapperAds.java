package ru.skypro.homework.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class ResponseWrapperAds <A> {
    private final int count;
    private final Collection <A> results;

    public ResponseWrapperAds( Collection<A> results) {
        this.count = results.size();
        this.results = results;
    }
}
