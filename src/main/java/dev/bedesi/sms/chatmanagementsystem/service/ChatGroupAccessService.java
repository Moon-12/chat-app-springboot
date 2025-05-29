package dev.bedesi.sms.chatmanagementsystem.service;
import dev.bedesi.sms.chatmanagementsystem.dto.JoinGroupDTO;
import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatGroupAccessEntity;
import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatGroupEntity;
import dev.bedesi.sms.chatmanagementsystem.mysql.repository.ChatGroupAccessRepository;
import dev.bedesi.sms.chatmanagementsystem.mysql.repository.ChatGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatGroupAccessService {
    @Autowired
    private ChatGroupAccessRepository chatGroupAccessRepository;
    @Autowired
    private ChatGroupRepository chatGroupRepository;

    public String joinGroup(JoinGroupDTO joinGroupRequestObj) {
        int groupId = joinGroupRequestObj.getGroupId();
        String userId = joinGroupRequestObj.getUserId();

        // Validate group existence
        Optional<ChatGroupEntity> group = chatGroupRepository.findById(groupId);
        if (!group.isPresent()) {
            throw new IllegalArgumentException ("Group not found");
        }

        // Check if user already has access
        Optional<ChatGroupAccessEntity> access = chatGroupAccessRepository.findUserExists(groupId, userId);
        if (access.isPresent()) {
            return access.get().getActive() ? "Already a member" : "Pending request";
        }

        // Create new access request
        ChatGroupAccessEntity newAccess = new ChatGroupAccessEntity();
        newAccess.setUserId(userId);
        newAccess.setChatGroup(group.get());
        //by default active is false
        chatGroupAccessRepository.save(newAccess);

        return "Request sent";
    }
}
