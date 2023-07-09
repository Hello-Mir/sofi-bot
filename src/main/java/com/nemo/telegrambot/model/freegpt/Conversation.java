package com.nemo.telegrambot.model.freegpt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Conversation {
    private String role;
    private String content;

    @Override
    public String toString() {
        return "Conversation{" +
                "role='" + role + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}