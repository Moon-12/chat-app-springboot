package dev.bedesi.sms.chatmanagementsystem.controller;

import dev.bedesi.sms.chatmanagementsystem.dto.ChatDTO;
import dev.bedesi.sms.chatmanagementsystem.dto.GetChatApiResponseDTO;

import dev.bedesi.sms.chatmanagementsystem.dto.PostMessageResponseDTO;
import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatEntity;
import dev.bedesi.sms.chatmanagementsystem.service.ChatService;
import dev.bedesi.sms.chatmanagementsystem.utils.AuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

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
    public SseEmitter subscribeToGroup(@RequestParam("group_id") int groupId) {
        logger.info("/getLatestMessage {}", groupId);
        logger.info("user {}", AuthUtil.getCurrentUserEmail());

        chatService.validateGroupAndAccess(groupId, AuthUtil.getCurrentUserEmail());

        SseEmitter emitter = new SseEmitter(0L);
        groupEmitters.computeIfAbsent(groupId, k -> new CopyOnWriteArrayList<>()).add(emitter);

        try {
            emitter.send(SseEmitter.event()
                    .comment("connected")   // <-- opens the stream
            );
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        emitter.onCompletion(() -> groupEmitters.get(groupId).remove(emitter));
        emitter.onTimeout(() -> groupEmitters.get(groupId).remove(emitter));
        emitter.onError(e -> groupEmitters.get(groupId).remove(emitter));

        //Optional and not a recommended way too much overhead on backend: send a heartbeat to keep connection alive
//        ScheduledFuture<?> heartbeatTask = heartbeatScheduler.scheduleAtFixedRate(
//                () -> {
//                    try {
//                        emitter.send(SseEmitter.event().name("heartbeat").data("ping"));
//                    } catch (IOException e) {
//                        emitter.completeWithError(e);
//                    }
//                },
//                Instant.now().plusSeconds(60),  // start after 20s
//                Duration.ofSeconds(60)          // repeat every 20s
//        );


        return emitter;
    }

    @PostMapping("/postMessageToGroup")
    public ResponseEntity<PostMessageResponseDTO> postTweet(@RequestBody ChatEntity chatEntity) {
        logger.info("chat entity {}",chatEntity);
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
            return ResponseEntity.ok(new PostMessageResponseDTO(chatDTO,"Message posted successfully!"));
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PostMessageResponseDTO(HttpStatus.BAD_REQUEST,e.getMessage()));
        }
        catch (Exception e) {
            logger.error("Error posting message: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PostMessageResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR,"An error occurred while processing the request"));
        }

    }
}
