package com.nemo.telegrambot.model.freegpt;

public enum Role {
    USER,
    ASSISTANT;

    public String getInLowCase() {
        return this.name().toLowerCase();
    }

}
