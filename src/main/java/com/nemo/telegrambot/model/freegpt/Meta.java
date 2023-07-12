package com.nemo.telegrambot.model.freegpt;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.*;

@JsonNaming(SnakeCaseStrategy.class)
@Getter
@Setter
public class Meta {
    private String id;
    private Content content;

    @Override
    public String toString() {
        return "Meta{" +
                "id='" + id + '\'' +
                ", content=" + content +
                '}';
    }
}
