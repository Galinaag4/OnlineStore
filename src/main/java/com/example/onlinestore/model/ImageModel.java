package com.example.onlinestore.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Lob
    private byte[] image;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    private AdsModel adsModel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    public AdsModel getAdsModel() {
        return adsModel;
    }

    public void setAdsModel(AdsModel adsModel) {
        this.adsModel = adsModel;
    }


}
