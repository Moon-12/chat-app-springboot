package dev.bedesi.sms.chatmanagementsystem.controller;

import dev.bedesi.sms.chatmanagementsystem.dto.ChatDTO;
import dev.bedesi.sms.chatmanagementsystem.dto.GetChatApiResponseDTO;

import dev.bedesi.sms.chatmanagementsystem.dto.ToastResponseDTO;
import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatEntity;
import dev.bedesi.sms.chatmanagementsystem.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class ChatController {
    @Autowired
    ChatService chatService;

    private final Map<Integer, List<SseEmitter>> groupEmitters = new ConcurrentHashMap<>();

    @GetMapping("/getAllPreviousMessages")
    public ResponseEntity<GetChatApiResponseDTO> getAllPreviousMessagesByGroupId(
            @RequestParam("group_id") int groupId) {
        try {
            List<ChatDTO> chatDTOList = chatService.getAllChatEntitiesByGroupId(groupId);

            String message = chatDTOList.isEmpty()
                    ? "No messages found for this group"
                    : "Messages retrieved successfully";

            return ResponseEntity.ok(new GetChatApiResponseDTO(chatDTOList, message)
            );
        } catch (SecurityException e) {
            // 403
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new GetChatApiResponseDTO(HttpStatus.FORBIDDEN,e.getMessage()));
        } catch (NoSuchElementException e) {
            // 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GetChatApiResponseDTO(HttpStatus.NOT_FOUND,e.getMessage()));
        } catch (IllegalArgumentException e) {
            // 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GetChatApiResponseDTO(HttpStatus.BAD_REQUEST,e.getMessage()));
        } catch (Exception e) {
            // 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GetChatApiResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR,"An error occurred while processing the request"));
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
