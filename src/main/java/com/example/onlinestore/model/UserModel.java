package com.example.onlinestore.model;



import com.example.onlinestore.dto.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
/**
 * Class of UserModel (пользователь).
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserModel  {
    /**
     * "id пользователя"
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "userName")
    private String username;
    /**
     * "имя"
     */
    @Column(name = "first_name")
    private String firstName;
    /**
     * "фамилия"
     */
    @Column(name = "last_name")
    private String lastName;
    /**
     * "номер телефона"
     */
    @Column(name = "phone")
    private String phone;
    /**
     * "тип пользователя" (USER, ADMIN)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    /**
     * "пароль пользователя"
     */
    @Column(name = "password")
    private String password;
    /**
     * "изображение пользователя"
     */
    @OneToOne
    private ImageModel imageModel;
    /**
     * "объявления пользователя"
     */
    @ToString.Exclude
    @OneToMany(mappedBy = "userModel",fetch = FetchType.LAZY)
    private Set<AdsModel> adsModels;




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<AdsModel> getAdsModels() {
        return adsModels;
    }

    public void setAdsModels(Set<AdsModel> adsModels) {
        this.adsModels = adsModels;
    }


    public ImageModel getImageModel() {
        return imageModel;
    }

    public void setImageModel(ImageModel imageModel) {
        this.imageModel = imageModel;
    }
}