package dev.bedesi.sms.chatmanagementsystem.controller;

import dev.bedesi.sms.chatmanagementsystem.dto.ChatDTO;
import dev.bedesi.sms.chatmanagementsystem.dto.GetChatApiResponseDTO;

import dev.bedesi.sms.chatmanagementsystem.dto.ToastResponseDTO;
import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatEntity;
import dev.bedesi.sms.chatmanagementsystem.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class ChatController {
    @Autowired
    ChatService chatService;

    private final Map<Integer, List<SseEmitter>> groupEmitters = new ConcurrentHashMap<>();

    @GetMapping("/getAllPreviousMessages")
    public ResponseEntity<GetChatApiResponseDTO> getAllPreviousMessagesByGroupId(@RequestParam("user_id") String userId,@RequestParam("group_id") int groupId) {
        try {
            Optional<List<ChatDTO>> chatDTOList = chatService.getAllChatEntitiesByGroupId(groupId, userId.toLowerCase());
            return chatDTOList.map(chatDTOS -> ResponseEntity.ok(new GetChatApiResponseDTO(chatDTOS, "Messages retrieved successfully"))).orElseGet(() -> ResponseEntity.status(404).body(new GetChatApiResponseDTO("No messages found for the given group and user")));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new GetChatApiResponseDTO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new GetChatApiResponseDTO("An error occurred while processing the request"));
        }
    }


    // Endpoint to subscribe to messages using SSE
    @GetMapping(value = "/getLatestMessage", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeToGroup(@RequestParam("group_id") int groupId,@RequestParam("user_id") String userId) {
        chatService.validateGroupAndAccess(groupId,userId.toLowerCase());

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        groupEmitters.computeIfAbsent(groupId, k -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> groupEmitters.getOrDefault(groupId, List.of()).remove(emitter));
        emitter.onTimeout(() -> groupEmitters.getOrDefault(groupId, List.of()).remove(emitter));
        emitter.onError((e) -> groupEmitters.getOrDefault(groupId, List.of()).remove(emitter));
        return emitter;
    }

    @PostMapping("/postMessageToGroup")
    public ResponseEntity<ToastResponseDTO> postTweet(@RequestBody ChatEntity chatEntity) {
        try {
            //save to db
            ChatDTO chatDTO = chatService.createChatEntity(chatEntity);
            // Broadcast to all connected clients
            List<SseEmitter> emitters = groupEmitters.get(chatDTO.getGroupId());
            if (emitters != null) {
                {
                    for (SseEmitter emitter : emitters) {
                        try {
                            emitter.send(SseEmitter.event()
                                    .name("newMessage")
                                    .data(chatDTO));
                        } catch (IOException e) {
                            emitter.completeWithError(e);
                            emitters.remove(emitter);
                        }
                    }
                }
            }
            return ResponseEntity.ok(new ToastResponseDTO("Message posted successfully!"));
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ToastResponseDTO(e.getMessage()));
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body(new ToastResponseDTO("An error occurred while processing the request"));
        }

    }
}
