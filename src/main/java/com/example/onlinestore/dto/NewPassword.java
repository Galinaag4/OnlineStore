package com.example.onlinestore.dto;

import lombok.Data;

@Data
public class NewPassword {
    private String currentPassword;
    private String newPassword;

    public NewPassword() {
    }
}
