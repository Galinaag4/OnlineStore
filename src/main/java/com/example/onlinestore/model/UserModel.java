package com.example.onlinestore.model;



import com.example.onlinestore.dto.Role;
import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "userName")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;
    @Column(name = "role")
    private Role role;
    @Column(name = "password")
    private String password;

    @ToString.Exclude
    @OneToMany(mappedBy = "author",fetch = FetchType.LAZY)
    private Set<AdsModel> adsModels;

    @Column(name = "reg_date")
    private String regDate;
    @Column(name = "city")
    private String city;
    @Column(name = "image")
    private String image;
}