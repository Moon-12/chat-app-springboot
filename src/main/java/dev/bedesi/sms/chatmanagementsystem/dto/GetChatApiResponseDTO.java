package dev.bedesi.sms.chatmanagementsystem.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetChatApiResponseDTO {
    // Getters
    private final List<ChatDTO> data;
    private final String message;

    // Constructor for success response
    public GetChatApiResponseDTO(List<ChatDTO> data, String message) {
        this.data = data != null ? data : new ArrayList<>();
        this.message = message != null ? message : "";
    }

    // Constructor for error response
    public GetChatApiResponseDTO(String message) {
        this.data = new ArrayList<>();
        this.message = message;
    }

}