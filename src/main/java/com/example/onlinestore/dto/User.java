package com.example.onlinestore.dto;

import lombok.Data;

@Data
public class User {
    public Integer id;
    public String email;
    public String firstName;
    public String lastName;
    public String phone;
    private String image;//ссылка

}
