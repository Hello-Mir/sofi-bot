package com.nemo.telegrambot.model.freegpt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parts {
    private String content;
    private String role;

    @Override
    public String toString() {
        return "Parts{" +
                "content='" + content + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
