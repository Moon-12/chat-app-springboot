package dev.bedesi.sms.chatmanagementsystem.service;
import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatEntity;
import dev.bedesi.sms.chatmanagementsystem.mysql.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;


    public Optional<List<ChatEntity>> getAllChatEntities() {
        return chatRepository.findAllActive();
    }

    public ChatEntity createChatEntity(ChatEntity ChatEntity) {
        return chatRepository.save(ChatEntity);
    }



}
