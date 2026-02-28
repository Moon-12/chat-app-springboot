package dev.bedesi.sms.chatmanagementsystem.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PostMessageResponseDTO {
    private final int status;
    private final String message;
    private final ChatDTO data;


    // Success response
    public PostMessageResponseDTO(ChatDTO data, String message) {
        this.status = HttpStatus.OK.value();
        this.message = message;
        this.data = data;
    }

    // Error response
    public PostMessageResponseDTO(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
        this.data=null;
    }
}
