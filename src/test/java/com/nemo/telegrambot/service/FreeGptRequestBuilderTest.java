package com.nemo.telegrambot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nemo.telegrambot.model.freegpt.Content;
import com.nemo.telegrambot.model.freegpt.FreeGptRequest;
import com.nemo.telegrambot.model.freegpt.Model;
import com.nemo.telegrambot.model.freegpt.Parts;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FreeGptRequestBuilderTest {
    private final Model model = Model.GPT_3_5_TURBO;
    FreeGptRequestBuilder freeGptRequestBuilder = new FreeGptRequestBuilder();

    @Test
    public void correct_request_with_model_and_text() {
        // Arrange
        String message = "Testing FreeGPTRequest.";

        // Act
        FreeGptRequest freeGptRequest = freeGptRequestBuilder.buildFreeGptRequestld(model, null, message);
        Content content = freeGptRequest.getMeta().getContent();
        Parts parts = content.getParts().get(0);

        // Assert
        assertEquals("default", freeGptRequest.getJailbreak());
        assertEquals("gpt-3.5-turbo", freeGptRequest.getModel());
        assertNotNull(freeGptRequest.getConversationId());
        assertEquals("user", parts.getRole());
        assertEquals("Testing FreeGPTRequest.", parts.getContent());
        assertEquals(true, content.getInternetAccess());
        assertEquals("text", content.getContentType());
    }

    @Test
    public void correctSerializing() {
        // Arrange
        String message = "Testing FreeGPTRequest.";

        // Act
        FreeGptRequest freeGptRequest = freeGptRequestBuilder.buildFreeGptRequestld(model, null, message);
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("requestInJSON.json");
        try {
            String string = objectMapper.writeValueAsString(freeGptRequest);
            objectMapper.writeValue(file, string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}