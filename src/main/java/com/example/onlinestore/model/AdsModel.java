package com.example.onlinestore.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Data
@Table(name = "ads")
public class AdsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userModel;
    private String description;
    private String image;
    private Integer price;
    private String title;
    @OneToMany(mappedBy = "ads", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentModel> commentModels;


    public AdsModel() {

    }
}
