package com.nemo.telegrambot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity(name = "messages")
public class Message {
    @Id
    private Long id;
    private String body;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "created_at")
    private Timestamp createdAt;
}
