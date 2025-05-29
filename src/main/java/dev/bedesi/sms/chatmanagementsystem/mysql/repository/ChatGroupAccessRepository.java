package dev.bedesi.sms.chatmanagementsystem.mysql.repository;

import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatGroupAccessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatGroupAccessRepository extends JpaRepository<ChatGroupAccessEntity,Integer> {
    @Query("SELECT c FROM ChatGroupAccessEntity c WHERE c.userId = :userId and c.chatGroup.id=:groupId")
    Optional<ChatGroupAccessEntity> findUserExists(@Param("groupId") int groupId, @Param("userId") String userId);
}
