package dev.bedesi.sms.chatmanagementsystem.controller;
import dev.bedesi.sms.chatmanagementsystem.dto.ChatGroupDTO;
import dev.bedesi.sms.chatmanagementsystem.service.ChatGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ChatGroupController {
    @Autowired
    ChatGroupService chatGroupService;

    @GetMapping("/getAllChatGroups")
    public ResponseEntity<?> getAllChatEntities() {
        Optional<List<ChatGroupDTO>> chatGroupDTOList= chatGroupService.getAllChatGroupEntities();
        if (chatGroupDTOList.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("data",chatGroupDTOList);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
