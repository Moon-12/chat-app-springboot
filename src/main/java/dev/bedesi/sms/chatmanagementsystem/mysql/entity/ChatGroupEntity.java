package dev.bedesi.sms.chatmanagementsystem.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="chat_group")
public class ChatGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="created_at")
    private Timestamp createdAt;
    @Column(name="created_by")
    private String createdBy;
    @Column(name="active")
    private Boolean active=true;
    @OneToMany(mappedBy = "chatGroup", fetch = FetchType.LAZY)
    private List<ChatGroupAccessEntity> groupAccessEntities;

}
