package com.nemo.telegrambot.model.freegpt;

import java.util.List;

import org.glassfish.jersey.internal.guava.MoreObjects;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record UserRequest(
        List<Messages> messages,
        Model model,
        Double temperature,
        Integer presequence_penalty,
        Integer frequencyPenalty,
        Integer topP) {

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("messages", messages)
                .add("model", model)
                .add("temperature", temperature)
                .add("presence_penalty", presequence_penalty)
                .add("frequency_penalty", frequencyPenalty)
                .add("top_p", topP)
                .toString();
    }
}
