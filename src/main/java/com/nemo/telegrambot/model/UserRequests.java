package com.nemo.telegrambot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity(name = "user_requests")
public class UserRequests {
    @Id
    private Long id;
    @Column(name = "conversation_id")
    private String conversationId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "created_at")
    private Timestamp createdAt;
}
