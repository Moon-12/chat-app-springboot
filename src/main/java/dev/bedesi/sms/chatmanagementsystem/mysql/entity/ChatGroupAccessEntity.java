package dev.bedesi.sms.chatmanagementsystem.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="chat_group_access")
public class ChatGroupAccessEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="user_id")
    private String userId;
    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private ChatGroupEntity chatGroup;
    @Column(name="active")
    private Boolean active=true;


}
