package dev.bedesi.sms.chatmanagementsystem.dto;


import lombok.Getter;



@Getter

public class ToastResponseDTO {
    private final String message;

    public ToastResponseDTO(String message) {
        this.message = message;
    }

}
