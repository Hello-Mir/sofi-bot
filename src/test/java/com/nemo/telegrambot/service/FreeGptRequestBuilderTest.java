package com.nemo.telegrambot.service;

import com.nemo.telegrambot.model.freegpt.Content;
import com.nemo.telegrambot.model.freegpt.Conversation;
import com.nemo.telegrambot.model.freegpt.FreeGptRequest;
import com.nemo.telegrambot.model.freegpt.Model;
import org.junit.jupiter.api.Test;

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
        Conversation conversationFromPreparedMeta = content.getConversation().get(0);

        // Assert
        assertEquals("default", freeGptRequest.getJailbreak());
        assertEquals("gpt-3.5-turbo", freeGptRequest.getModel());
        assertNotNull(freeGptRequest.getConversationId());
        assertEquals("user", conversationFromPreparedMeta.getRole());
        assertEquals("Testing FreeGPTRequest.", conversationFromPreparedMeta.getContent());
        assertEquals(true, content.getInternetAccess());
        assertEquals("text", content.getContentType());
    }
}