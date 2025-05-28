package dev.bedesi.sms.chatmanagementsystem.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="CHAT_MESSAGE")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="created_by")
    private String createdBy;
    @Column(name="created_at")
    private Timestamp createdAt;
    @Column(name="message")
    private String message;
    @Column(name="active")
    private Boolean active=true;
    @Column(name="group_id")
    private int groupId;


}
