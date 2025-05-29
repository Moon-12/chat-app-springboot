package dev.bedesi.sms.chatmanagementsystem.service;
import dev.bedesi.sms.chatmanagementsystem.dto.ChatGroupAccessDTO;
import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatGroupAccessEntity;
import dev.bedesi.sms.chatmanagementsystem.mysql.repository.ChatGroupAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatGroupAccessService {

    @Autowired
    private ChatGroupAccessRepository chatGroupAccessRepository;


    public Optional<List<ChatGroupAccessDTO>> getAllChatGroupAccessEntitiesByUserId(String user_id) {
        Optional<List<ChatGroupAccessEntity> > chatGroupAccessEntityList= chatGroupAccessRepository.findGroupByUserAccess(user_id);
        List<ChatGroupAccessDTO> chatGroupAccessDTOList =new ArrayList<>();
        if(chatGroupAccessEntityList.isPresent()){
            chatGroupAccessDTOList= chatGroupAccessEntityList.get().stream().map(chatGroupAccessEntity -> {
                ChatGroupAccessDTO chatGroupAccessDTO=new ChatGroupAccessDTO();
                chatGroupAccessDTO.setAllFieldsFromEntity(chatGroupAccessEntity);
                return chatGroupAccessDTO;
            }).collect(Collectors.toList());
        }
        return Optional.of(chatGroupAccessDTOList);
    }


}
