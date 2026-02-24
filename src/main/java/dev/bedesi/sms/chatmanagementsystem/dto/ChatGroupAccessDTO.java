package dev.bedesi.sms.chatmanagementsystem.dto;

import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatGroupAccessEntity;
import lombok.Data;

@Data
public class ChatGroupAccessDTO implements TranslateDTO<ChatGroupAccessEntity> {
    private String name;
    private  int id;
    @Override
    public void setAllFieldsFromEntity(ChatGroupAccessEntity entity) {
        this.id=entity.getChatGroup().getId();
        this.name=entity.getChatGroup().getName();

    }
}
