package com.nemo.telegrambot.service;

import com.nemo.telegrambot.model.freegpt.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static java.util.UUID.randomUUID;

@Service
public class FreeGptRequestBuilder {
    private FreeGptRequest freeGptRequest = new FreeGptRequest();


    public void prepareMainPart(Model model, String jailbreak) {
        freeGptRequest.setConversationId(randomUUID().toString());
        freeGptRequest.setAction("_ask");
        freeGptRequest.setModel(model.getValue());
        freeGptRequest.setJailbreak(jailbreak == null ? "default" : jailbreak);
    }

    public void prepareMeta(String message) {
        Meta meta = new Meta();
        meta.setId(randomUUID().toString());
        Content content = new Content();
        Conversation conversation = new Conversation();
        conversation.setContent(message);
        conversation.setRole(Role.USER.getInLowCase());
        content.setConversation(Collections.singletonList(conversation));

        content.setInternetAccess(true);
        content.setContentType("text");
        content.setParts(Collections.singletonList(new Parts()));
        meta.setContent(content);
        freeGptRequest.setMeta(meta);
    }

    public FreeGptRequest buildFreeGptRequestld() {
        return freeGptRequest;
    }
}
