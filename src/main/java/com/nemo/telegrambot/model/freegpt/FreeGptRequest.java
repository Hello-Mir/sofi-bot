package com.nemo.telegrambot.model.freegpt;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@JsonNaming(SnakeCaseStrategy.class)
@Getter
@Setter
public class FreeGptRequest {
    private String conversationId;
    private String action;
    private String model;
    private String jailbreak;
    private Meta meta;

    @Override
    public String toString() {
        return "FreeGptRequest{" +
                "conversationId='" + conversationId + '\'' +
                ", action='" + action + '\'' +
                ", model='" + model + '\'' +
                ", jailbreak='" + jailbreak + '\'' +
                ", meta=" + meta +
                '}';
    }
}
