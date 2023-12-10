package com.nemo.telegrambot.model.freegpt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "users")
public class User {
    @Id
    private long id;
    private String name;
    private int msg_number;
}
