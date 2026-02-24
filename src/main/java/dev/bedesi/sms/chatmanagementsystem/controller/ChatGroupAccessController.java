package dev.bedesi.sms.chatmanagementsystem.controller;

import dev.bedesi.sms.chatmanagementsystem.dto.JoinGroupDTO;
import dev.bedesi.sms.chatmanagementsystem.dto.ToastResponseDTO;
import dev.bedesi.sms.chatmanagementsystem.service.ChatGroupAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatGroupAccessController {
    @Autowired
    ChatGroupAccessService chatGroupAccessService;
    @PostMapping(value = "/joinGroup")
    public ResponseEntity<ToastResponseDTO> joinGroupController(@RequestBody JoinGroupDTO joinGroupRequestObj) {
        try {
            String result = chatGroupAccessService.joinGroup(joinGroupRequestObj);
            return ResponseEntity.ok(new ToastResponseDTO(result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ToastResponseDTO(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ToastResponseDTO("An error occurred while processing the join request"));
        }
    }
}
