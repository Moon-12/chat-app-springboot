package dev.bedesi.sms.chatmanagementsystem.mysql.repository;

import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatGroupAccessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatGroupAccessRepository extends JpaRepository<ChatGroupAccessEntity,String> {
    @Query("SELECT c FROM ChatGroupAccessEntity c WHERE c.active = true and c.userId=:user_id")
    Optional<List<ChatGroupAccessEntity>> findGroupByUserAccess(String user_id);
}
