package dev.bedesi.sms.chatmanagementsystem.controller;

import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatEntity;
import dev.bedesi.sms.chatmanagementsystem.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("chat/getAllPreviousMessages")
public class ChatController {
    @Autowired
    ChatService chatService;

    @GetMapping
    public Optional<List<ChatEntity>> getAllChatEntities() {
        return chatService.getAllChatEntities();
    }

    @PostMapping
    public ChatEntity createSampleEntity(@RequestBody ChatEntity chatEntity) {
        return chatService.createChatEntity(chatEntity);
    }


}
