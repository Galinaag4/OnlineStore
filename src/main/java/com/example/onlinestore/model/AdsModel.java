package com.example.onlinestore.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
@ToString
@EqualsAndHashCode
public class AdsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private UserModel userModel;
    private String description;
    private String image;
    private Integer price;
    private String title;

    public AdsModel(Long id /*UserModel userModel*/, String description, String image, Integer price, String title) {
        this.id = id;
        //this.userModel = userModel;
        this.description = description;
        this.image = image;
        this.price = price;
        this.title = title;
    }

    public AdsModel() {

    }
}
