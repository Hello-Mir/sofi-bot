package com.nemo.telegrambot.service;

import com.nemo.telegrambot.model.freegpt.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static java.util.UUID.randomUUID;

@Service
public class FreeGptRequestBuilder {
    private final FreeGptRequest freeGptRequest = new FreeGptRequest();


    private void prepareMainPart(Model model, String jailbreak) {
        freeGptRequest.setConversationId(randomUUID().toString());
        freeGptRequest.setAction("_ask");
        freeGptRequest.setModel(model.getValue());
        freeGptRequest.setJailbreak(jailbreak == null ? "default" : jailbreak);
    }

    private void prepareMeta(String message) {
        Content content = new Content();
        content.setConversation(Collections.singletonList(new Conversation()));
        content.setInternetAccess(true);
        content.setContentType("text");

        Parts parts = new Parts();
        parts.setContent(message);
        parts.setRole(Role.USER.getInLowCase());
        content.setParts(Collections.singletonList(parts));

        Meta meta = new Meta();
        meta.setId(randomUUID().toString());
        meta.setContent(content);
        freeGptRequest.setMeta(meta);
    }

    public FreeGptRequest buildFreeGptRequestld(Model model, String jailbreak, String message) {
        prepareMainPart(model, jailbreak);
        prepareMeta(message);
        return freeGptRequest;
    }
}
