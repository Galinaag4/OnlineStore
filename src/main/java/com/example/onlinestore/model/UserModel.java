package com.example.onlinestore.model;


import lombok.*;
import com.example.onlinestore.dto.Role;

import javax.persistence.*;

@Entity
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Data
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;

    public UserModel(Long id, String password, String firstName, String lastName, String phone, Role role, String image) {
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
        this.image = image;
    }

    public UserModel() {

    }





}
