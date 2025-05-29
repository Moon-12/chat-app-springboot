package dev.bedesi.sms.chatmanagementsystem.controller;

import dev.bedesi.sms.chatmanagementsystem.dto.ChatDTO;
import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatEntity;
import dev.bedesi.sms.chatmanagementsystem.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ChatController {
    @Autowired
    ChatService chatService;

    @GetMapping("/getAllPreviousMessages/{group_id}/{user_id}")
    public ResponseEntity<?> getAllPreviousMessagesByGroupId(@PathVariable("group_id") Long groupId,@PathVariable("user_id") String userId) {
        System.out.println("user id"+userId);
        Optional<List<ChatDTO>> chatDTOList= chatService.getAllChatEntitiesByGroupId(groupId);
        if (chatDTOList.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("data",chatDTOList);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
/*
    @PostMapping("/postMessage")
    public ResponseEntity<?> createSampleEntity(@RequestBody ChatEntity chatEntity) {
        ChatDTO chatDTO=chatService.createChatEntity(chatEntity);
        Map<String, Object> response = new HashMap<>();
        response.put("data",chatDTO);
        return ResponseEntity.ok(response);
    }
*/
    //socket api
    @MessageMapping("/postMessage")
    @SendTo("/topic/chat")
    public ChatDTO postMessage(ChatEntity chatEntity) {
        System.out.println("chat en"+chatEntity);
        return chatService.createChatEntity(chatEntity);
    }

}
