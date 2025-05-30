package dev.bedesi.sms.chatmanagementsystem.controller;

import dev.bedesi.sms.chatmanagementsystem.dto.ChatDTO;
import dev.bedesi.sms.chatmanagementsystem.dto.GetChatApiResponseDTO;
import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatEntity;
import dev.bedesi.sms.chatmanagementsystem.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ChatController {
    @Autowired
    ChatService chatService;

    @GetMapping("/getAllPreviousMessages")
    public ResponseEntity<GetChatApiResponseDTO> getAllPreviousMessagesByGroupId(@RequestParam("user_id") String userId,@RequestParam("group_id") int groupId) {
        try {
            Optional<List<ChatDTO>> chatDTOList = chatService.getAllChatEntitiesByGroupId(groupId, userId);
            return chatDTOList.map(chatDTOS -> ResponseEntity.ok(new GetChatApiResponseDTO(chatDTOS, "Messages retrieved successfully"))).orElseGet(() -> ResponseEntity.status(404).body(new GetChatApiResponseDTO("No messages found for the given group and user")));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new GetChatApiResponseDTO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new GetChatApiResponseDTO("An error occurred while processing the request"));
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
