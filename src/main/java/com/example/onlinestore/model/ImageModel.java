package com.example.onlinestore.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "path")
    private String path;
    @Lob
    @Type(type = "binary")
    private byte[] image;
    private String mediaType;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private AdsModel adsModel;




}
