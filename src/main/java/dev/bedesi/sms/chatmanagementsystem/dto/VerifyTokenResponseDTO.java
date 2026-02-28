package dev.bedesi.sms.chatmanagementsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class VerifyTokenResponseDTO {
    private UserDTO userProfile;
    private List<String> roles;


    public VerifyTokenResponseDTO(UserDTO userDTO,List<String> roles) {
        this.userProfile=userDTO;
        this.roles = roles;

    }
}
