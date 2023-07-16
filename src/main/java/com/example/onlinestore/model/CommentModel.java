package com.example.onlinestore.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
/**
 * Class of CommentModel (комментарий в объявлениях).
 */
@Entity
@Data
@Table(name = "comments")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CommentModel {
    /**
     * "id комментария"
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    /**
     * "время создания комментария"
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    /**
     * "текст комментария"
     */
    @Column(name = "text")
    private String text;
    /**
     * "автор комментария"
     */
    @ManyToOne
    private UserModel userModel;
    /**
     * "объявление"
     */
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


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
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
