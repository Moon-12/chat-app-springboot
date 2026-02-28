package dev.bedesi.sms.chatmanagementsystem.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
public class GetChatApiResponseDTO {

    private final boolean success;
    private final int status;
    private final String message;
    private final List<ChatDTO> data;


    // Success response
    public GetChatApiResponseDTO(List<ChatDTO> data, String message) {
        this.success = true;
        this.status = HttpStatus.OK.value();
        this.message = message;
        this.data = data != null ? data : Collections.emptyList();
    }

    // Error response
    public GetChatApiResponseDTO(HttpStatus status, String message) {
        this.success = false;
        this.status = status.value();
        this.message = message;
        this.data=null;
    }
}