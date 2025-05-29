package dev.bedesi.sms.chatmanagementsystem.controller;
import dev.bedesi.sms.chatmanagementsystem.dto.ChatGroupAccessDTO;
import dev.bedesi.sms.chatmanagementsystem.service.ChatGroupAccessService;
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
    ChatGroupAccessService chatGroupAccessService;

    @GetMapping("/getAllChatGroups")
    public ResponseEntity<?> getChatGroupByUserId(@RequestParam("user_id") String user_id) {
        Optional<List<ChatGroupAccessDTO>> chatGroupAccessDTOList= chatGroupAccessService.getAllChatGroupAccessEntitiesByUserId(user_id);
        if (chatGroupAccessDTOList.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("data",chatGroupAccessDTOList);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
