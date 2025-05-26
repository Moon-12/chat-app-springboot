package dev.bedesi.sms.chatmanagementsystem.dto;

import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatGroupEntity;
import lombok.Data;

@Data
public class ChatGroupDTO implements TranslateDTO<ChatGroupEntity> {
    private String name;
    private  int id;
    @Override
    public void setAllFieldsFromEntity(ChatGroupEntity chatGroupEntity) {
        this.id=chatGroupEntity.getId();
        this.name=chatGroupEntity.getName();


    }
}
