package dev.bedesi.sms.chatmanagementsystem.service;
import dev.bedesi.sms.chatmanagementsystem.dto.ChatDTO;
import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatEntity;
import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatGroupEntity;
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

    @Autowired
    private  ChatGroupService chatGroupService;

    @Autowired
    private ChatGroupAccessService chatGroupAccessService;

    public void validateGroupAndAccess(int groupId, String userId) {
        Optional<ChatGroupEntity> group = chatGroupService.checkGroupExists(groupId);
        if (group.isEmpty()) {
            throw new IllegalArgumentException("Group not found");
        }
        if (!chatGroupAccessService.checkUserAccess(groupId, userId)) {
            throw new IllegalArgumentException("User has no access");
        }
    }

    public Optional<List<ChatDTO>> getAllChatEntitiesByGroupId(int groupId,String userId) {
        // Validate group and user access
        validateGroupAndAccess(groupId, userId);

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

    public ChatDTO createChatEntity(ChatEntity chatEntity) {
        // Validate group and user access
        validateGroupAndAccess(chatEntity.getGroupId(), chatEntity.getCreatedBy());
        ChatDTO chatDTO=new ChatDTO();
        chatDTO.setAllFieldsFromEntity( chatRepository.save(chatEntity));
        return chatDTO;
    }

}
