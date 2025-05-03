package dev.bedesi.sms.chatmanagementsystem.controller;

import dev.bedesi.sms.chatmanagementsystem.dto.ChatDTO;
import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatEntity;
import dev.bedesi.sms.chatmanagementsystem.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("chat")
public class ChatController {
    @Autowired
    ChatService chatService;

    @GetMapping("/getAllPreviousMessages")
    public Optional<List<ChatDTO>> getAllChatEntities() {
        return chatService.getAllChatEntities();
    }

    @PostMapping("/postMessage")
    public ChatDTO createSampleEntity(@RequestBody ChatEntity chatEntity) {
        return chatService.createChatEntity(chatEntity);
    }


}
