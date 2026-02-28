package dev.bedesi.sms.chatmanagementsystem.controller;
import dev.bedesi.sms.chatmanagementsystem.dto.ChatGroupDTO;
import dev.bedesi.sms.chatmanagementsystem.service.ChatGroupService;

import dev.bedesi.sms.chatmanagementsystem.utils.AuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ChatGroupController {
    @Autowired
    ChatGroupService chatGroupService;

    private static final Logger logger = LoggerFactory.getLogger(ChatGroupController.class);

    @PreAuthorize("hasRole('VIEWER')")
    @GetMapping("/getAllChatGroups")
    public ResponseEntity<?> getChatGroupByUserId( ) {

        logger.info("user email {}",AuthUtil.getCurrentUserEmail());
        Optional<List<ChatGroupDTO>> chatGroupDTOList= chatGroupService.getAllChatGroupEntitiesByUserId(AuthUtil.getCurrentUserEmail());
        if (chatGroupDTOList.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("data",chatGroupDTOList);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
