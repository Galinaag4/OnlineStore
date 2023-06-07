package com.example.onlinestore.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;


@Entity
@Table(name = "image")
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class ImageModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Lob
    @Type(type = "binary")
    private byte[] image;

   private String filePath;

    private String mediaType;


}
