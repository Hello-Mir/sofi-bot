package com.nemo.telegrambot.service;

import com.nemo.telegrambot.clients.feign.LocalhostClient;
import com.nemo.telegrambot.model.freegpt.FreeGptRequest;
import org.springframework.stereotype.Service;

@Service
public class FreeGptService {
    private final LocalhostClient client;

    public FreeGptService(LocalhostClient client) {
        this.client = client;
    }

    public String sendRequest(String acceptHeader, FreeGptRequest request) {
        return client.sendRequest(acceptHeader, request);
    }

}
