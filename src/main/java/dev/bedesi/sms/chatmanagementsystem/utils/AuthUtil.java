package dev.bedesi.sms.chatmanagementsystem.utils;

import dev.bedesi.sms.chatmanagementsystem.dto.VerifyTokenResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class AuthUtil {
    public static VerifyTokenResponseDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof VerifyTokenResponseDTO user) {
            return user;
        }
        return null;
    }

    public static String getCurrentUserEmail() {
        VerifyTokenResponseDTO user = getCurrentUser();
        return user != null ? user.getUserProfile().getEmail() : "unknown";
    }
}
