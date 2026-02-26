package dev.bedesi.sms.chatmanagementsystem.service;
import dev.bedesi.sms.chatmanagementsystem.dto.ChatDTO;
import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatEntity;
import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatGroupEntity;
import dev.bedesi.sms.chatmanagementsystem.mysql.repository.ChatRepository;
import dev.bedesi.sms.chatmanagementsystem.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        // 404 → Group not found
        if (group.isEmpty()) {
            throw new NoSuchElementException("Group not found");
        }
        // 403 → User has no access
        if (!chatGroupAccessService.checkUserAccess(groupId, userId)) {
            throw new SecurityException("You are not authorized to access this group");
        }
    }
    public List<ChatDTO> getAllChatEntitiesByGroupId(int groupId) {
        // 403 → user not allowed
        validateGroupAndAccess(groupId, AuthUtil.getCurrentUserEmail());

        Optional<List<ChatEntity>> chatEntityList= chatRepository.findAllActiveByGroupId(groupId);

        // 404 → no messages
        if (chatEntityList.isEmpty() || chatEntityList.get().isEmpty()) {
            return List.of();
        }

        return chatEntityList.get().stream().map(chatEntity -> {
                   ChatDTO chatDTO=new ChatDTO();
                   chatDTO.setAllFieldsFromEntity(chatEntity);
                   return chatDTO;
               }).toList();
    }

    public ChatDTO createChatEntity(ChatEntity chatEntity) {
        // Validate group and user access
        validateGroupAndAccess(chatEntity.getGroupId(), chatEntity.getCreatedBy());
        ChatDTO chatDTO=new ChatDTO();
        chatDTO.setAllFieldsFromEntity( chatRepository.save(chatEntity));
        return chatDTO;
    }

}
