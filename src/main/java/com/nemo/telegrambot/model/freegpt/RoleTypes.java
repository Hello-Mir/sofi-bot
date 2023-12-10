package com.nemo.telegrambot.model.freegpt;

public enum RoleTypes {
    ASSISTANT,
    USER;

    public String getRoleTypeLowCase() {
        return this.name().toLowerCase();
    }
}
