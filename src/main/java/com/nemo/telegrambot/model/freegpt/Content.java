package com.nemo.telegrambot.model.freegpt;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

@JsonNaming(SnakeCaseStrategy.class)
@Getter
@Setter
public class Content {
    private List<Conversation> conversation;
    private Boolean internetAccess;
    private String contentType;
    private List<Parts> parts;

    @Override
    public String toString() {
        return "Content{" +
                "conversation=" + conversation +
                ", internetAccess=" + internetAccess +
                ", contentType='" + contentType + '\'' +
                ", parts=" + parts +
                '}';
    }
}
