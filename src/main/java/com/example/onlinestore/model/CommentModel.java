package com.example.onlinestore.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@EqualsAndHashCode
@ToString
public class CommentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private UserModel userModel;
    private Integer pk;//? надо его тут создавать или нет?
    private String text;
    private LocalDateTime createdAt;

    public CommentModel(Long id /*UserModel userModel*/, Integer pk, String text, LocalDateTime createdAt) {
        this.id = id;
        //this.userModel = userModel;
        this.pk = pk;
        this.text = text;
        this.createdAt = createdAt;
    }

    public CommentModel() {

    }

}
