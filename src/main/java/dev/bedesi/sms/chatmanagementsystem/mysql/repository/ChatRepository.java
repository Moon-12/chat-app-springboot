package dev.bedesi.sms.chatmanagementsystem.mysql.repository;

import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<ChatEntity,Integer> {
    @Query("SELECT * FROM CHAT_MESSAGE c WHERE c.active = true")
    Optional<List<ChatEntity>> findAllActive();
}
