package com.example.onlinestore.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@ToString
@Table(name = "comment")
public class CommentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userModel;
    @ManyToOne
    @JoinColumn(name = "ads_id")
    private AdsModel ads;
    private String text;
    private LocalDateTime createdAt;

    public CommentModel(Long id, UserModel userModel, AdsModel ads, String text, LocalDateTime createdAt) {
        this.id = id;
        this.userModel = userModel;
        this.ads = ads;
        this.text = text;
        this.createdAt = createdAt;
    }

    public CommentModel() {

    }

}
