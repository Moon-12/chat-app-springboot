package dev.bedesi.sms.chatmanagementsystem.mysql.repository;

import dev.bedesi.sms.chatmanagementsystem.mysql.entity.ChatGroupAccessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatGroupAccessRepository extends JpaRepository<ChatGroupAccessEntity,String> {

}
