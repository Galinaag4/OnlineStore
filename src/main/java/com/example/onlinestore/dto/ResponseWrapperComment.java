package com.example.onlinestore.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class ResponseWrapperComment<C> {
    private  int count;
    private  Collection<C> results;


}
