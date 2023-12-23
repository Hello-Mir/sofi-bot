package com.nemo.telegrambot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nemo.telegrambot.model.freegpt.Messages;
import com.nemo.telegrambot.model.freegpt.UserRequest;

@Service
public class UserRequestBuilder {
    private static UserRequest userRequest;

    public UserRequestBuilder() {
    
    }

    public static UserRequestBuilder withMessages(
        List<Messages> messages,String model,
         Double temperature, int presencePenalty, int frequencyPenalty, int topP
         ) {
     userRequest = new UserRequest(messages, model, temperature, presencePenalty, frequencyPenalty, topP);

        return this;
    }
    /*
     * List<Messages> messages,
     * String model,
     * Double temperature,
     * Integer presequence_penalty,
     * Integer frequencyPenalty,
     * Integer topP
     */

    private UserRequest build() {
        return userRequest;
    }
}
