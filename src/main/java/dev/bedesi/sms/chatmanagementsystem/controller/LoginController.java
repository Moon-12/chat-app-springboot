package dev.bedesi.sms.chatmanagementsystem.controller;

import dev.bedesi.sms.chatmanagementsystem.dto.LoginRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    @PostMapping("/login")
    public ResponseEntity<?> loginApp(@RequestBody LoginRequestDTO loginRequest) {
        Map<String, Object> response = new HashMap<>();
        if (loginRequest.getPassword().equals(System.getenv("APP_PASSWORD"))) {
            response.put("message", "Login Successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid Credentials");
            return  ResponseEntity.status(401).body(response);
        }

    }
}
