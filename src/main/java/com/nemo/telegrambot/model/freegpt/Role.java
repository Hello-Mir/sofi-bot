package com.nemo.telegrambot.model.freegpt;

public enum Role {
    USER,
    ASSISTANT;

    public String getLowerCase() {
        return this.name().toLowerCase();
    }

}
