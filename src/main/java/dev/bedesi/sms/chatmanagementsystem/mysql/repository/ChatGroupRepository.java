package dev.bedesi.sms.chatmanagementsystem.mysql.repository;

import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatGroupRepository extends JpaRepository<ChatGroupEntity,Integer> {
    @Query("SELECT c FROM ChatGroupEntity c WHERE c.active = true")
    Optional<List<ChatGroupEntity>> findAllActive();
}
