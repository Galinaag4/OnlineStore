package com.example.onlinestore.model;

import com.example.onlinestore.dto.Comment;
import com.example.onlinestore.dto.User;
import lombok.*;

import javax.persistence.*;
import java.awt.*;
import java.util.List;

@Entity
@Data
@Table(name = "ads")
public class AdsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel author;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private int price;

    @Column(name = "description")
    private String description;

    @ToString.Exclude
    @OneToMany(mappedBy = "ads", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Image> images;
}
