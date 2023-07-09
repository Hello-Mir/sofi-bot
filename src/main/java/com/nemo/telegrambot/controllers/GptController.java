package com.nemo.telegrambot.controllers;

import com.nemo.telegrambot.clients.feign.LocalhostClient;
import com.nemo.telegrambot.model.freegpt.FreeGptRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping()
public class GptController {
    private final LocalhostClient localhostClient;

    public GptController(LocalhostClient localhostClient) {
        this.localhostClient = localhostClient;
    }

    @PostMapping("/backend-api/v2/conversation")
    public String sendRequest(@RequestHeader("Accept") String acceptType, @RequestBody FreeGptRequest freeGptRequest) {
        return localhostClient.sendRequest(acceptType, freeGptRequest);
    }

}
