package dev.bedesi.sms.chatmanagementsystem.dto;

import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatGroupEntity;
import lombok.Data;

@Data
public class ChatGroupDTO implements TranslateDTO<ChatGroupEntity> {
    private String name;
    private  int id;
    private String activeAccess;
    @Override
    public void setAllFieldsFromEntity(ChatGroupEntity chatGroupEntity) {
        this.id=chatGroupEntity.getId();
        this.name=chatGroupEntity.getName();

    }
    public void setFieldsFromQueryResult(Object[] queryResult) {
        this.id = (Integer) queryResult[0];
        this.name = (String) queryResult[1];
        this.activeAccess = (String) queryResult[2];
    }
}
