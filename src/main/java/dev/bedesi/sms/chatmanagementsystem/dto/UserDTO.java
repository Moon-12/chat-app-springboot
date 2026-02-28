package dev.bedesi.sms.chatmanagementsystem.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String userName;
    private String email;

    public UserDTO(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }
}
