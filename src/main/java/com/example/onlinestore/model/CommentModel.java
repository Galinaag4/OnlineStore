package com.example.onlinestore.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "comments")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CommentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "text")
    private String text;
    @ManyToOne
    private UserModel userModel;
    @ManyToOne
    private AdsModel adsModel;

    public CommentModel() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public AdsModel getAdsModel() {
        return adsModel;
    }

    public void setAdsModel(AdsModel adsModel) {
        this.adsModel = adsModel;
    }
}
