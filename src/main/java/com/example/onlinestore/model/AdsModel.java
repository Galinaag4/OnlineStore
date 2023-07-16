package com.example.onlinestore.model;

import lombok.*;

import javax.persistence.*;
/**
 * Class of AdsModel (объявление).
 */
@Entity
@Data
@Table(name = "ads")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AdsModel {
    /**
     * "id объявления"
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * "автор объявления"
     */
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel userModel;
    /**
     * "заголовок объявления"
     */
    @Column(name = "title")
    private String title;

    /**
     * "цена товара"
     */
    @Column(name = "price")
    private int price;
    /**
     * "описание товара"
     */
    @Column(name = "description")
    private String description;

    /**
     * "изображение товара"
     */
    @ToString.Exclude
    @OneToOne(mappedBy = "adsModel", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private ImageModel imageModel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public ImageModel getImageModel() {
        return imageModel;
    }

    public void setImageModel(ImageModel imageModel) {
        this.imageModel = imageModel;
    }
}
