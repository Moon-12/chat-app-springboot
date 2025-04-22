package dev.bedesi.sms.samplemanagementsystem.dto;

import dev.bedesi.sms.samplemanagementsystem.mysql.entity.ChatEntity;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChatDTO implements TranslateDTO<ChatEntity> {
    private int id;
    private String createdBy;
    private Timestamp createdAt;


    @Override
    public void setAllFieldsFromEntity(ChatEntity chatEntity) {
        this.id=chatEntity.getId();
        this.createdBy=chatEntity.getCreatedBy();
        this.createdAt=chatEntity.getCreatedAt();

    }
}
