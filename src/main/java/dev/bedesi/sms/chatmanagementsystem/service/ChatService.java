package dev.bedesi.sms.chatmanagementsystem.service;
import dev.bedesi.sms.chatmanagementsystem.dto.ChatDTO;
import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatEntity;
import dev.bedesi.sms.chatmanagementsystem.mysql.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;


    public Optional<List<ChatDTO>> getAllChatEntitiesByGroupId(Long groupId) {
        Optional<List<ChatEntity>> chatEntityList= chatRepository.findAllActiveByGroupId(groupId);
        List<ChatDTO> chatDTOList=new ArrayList<>();
       if(chatEntityList.isPresent()){
           chatDTOList= chatEntityList.get().stream().map(chatEntity -> {
               ChatDTO chatDTO=new ChatDTO();
               chatDTO.setAllFieldsFromEntity(chatEntity);
               return chatDTO;
           }).collect(Collectors.toList());
       }
       return Optional.of(chatDTOList);
    }

    public ChatDTO createChatEntity(ChatEntity ChatEntity) {
        ChatDTO chatDTO=new ChatDTO();
        chatDTO.setAllFieldsFromEntity( chatRepository.save(ChatEntity));
        return chatDTO;
    }

}
