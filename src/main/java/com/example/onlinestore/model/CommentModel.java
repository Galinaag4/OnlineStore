package com.example.onlinestore.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "comments")
public class CommentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ads_id")
    private Integer adsId;

    @Column(name = "author_id")
    private Integer author;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "text")
    private String text;

}
