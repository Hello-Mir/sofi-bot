package com.nemo.telegrambot;

import com.nemo.telegrambot.components.BotCommands;
import com.nemo.telegrambot.components.Buttons;
import com.nemo.telegrambot.config.BotConfig;
import com.nemo.telegrambot.controllers.GptController;
import com.nemo.telegrambot.database.MessageRepository;
import com.nemo.telegrambot.database.UserRepository;
import com.nemo.telegrambot.model.User;
import com.nemo.telegrambot.model.freegpt.FreeGptRequest;
import com.nemo.telegrambot.model.freegpt.Model;
import com.nemo.telegrambot.service.FreeGptRequestBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class GptBot extends TelegramLongPollingBot {
    private final BotConfig config;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final GptController gptController;
    private final FreeGptRequestBuilder requestBuilder;

    public GptBot(BotConfig config, UserRepository userRepository,
                  MessageRepository messageRepository, GptController gptController, FreeGptRequestBuilder requestBuilder) {
        this.config = config;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.gptController = gptController;
        this.requestBuilder = requestBuilder;
    }


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = 0;
        long userId = 0;
        String userName = null;
        String receivedMessage;

        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
            userName = update.getMessage().getFrom().getFirstName();

            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                botAnswerUtils(receivedMessage, chatId, userName);
            }
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userId = update.getCallbackQuery().getFrom().getId();
            userName = update.getCallbackQuery().getFrom().getFirstName();
            receivedMessage = update.getCallbackQuery().getData();

            botAnswerUtils(receivedMessage, chatId, userName);
        }

        if (chatId == Long.parseLong(config.getChatId())) {
            updateDB(userId, userName);
        }
    }

    private void botAnswerUtils(String receivedMessage, long chatId, String userName) {
        switch (receivedMessage) {
            case "/start" -> startBot(chatId, userName);
            case "/delete_all_my_questions" -> sendDeleteAllMyMessagesText(chatId);
            case "/help" -> sendHelpText(chatId);
            default -> sendMessageToGPT(chatId, receivedMessage);
        }
    }

    private void startBot(long chatId, String userName) {
        String startText = "Здравствуйте, " + userName + "\n. Я Софи - Ваш помощник.\nПередам Ваши запросы в GPT.";
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(startText);
        message.setReplyMarkup(Buttons.inlineMarkup());
        replyToUser(message);
    }

    private void sendHelpText(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(BotCommands.HELP_TEXT);
        replyToUser(message);
    }

    private void sendMessageToGPT(long chatId, String text) {
        requestBuilder.prepareMainPart(Model.GPT_3_5_TURBO, "default");
        requestBuilder.prepareMeta(text);
        FreeGptRequest freeGptRequest = requestBuilder.buildFreeGptRequestld();
        String body = gptController.sendRequest("text/event-stream", freeGptRequest);

        messageRepository.saveMessage(text, chatId);
        SendMessage message = new SendMessage();

        message.setText(body);
        message.setChatId(chatId);
        replyToUser(message);
    }

    private void sendDeleteAllMyMessagesText(long chatId) {
        messageRepository.deleteAllMessagesForUser(chatId);
        SendMessage message = new SendMessage();
        message.setText("История Ваших запросов очищена из Базы.");
        message.setChatId(chatId);
        replyToUser(message);
    }

    private void updateDB(long userId, String userName) {
        if (userRepository.findById(userId).isEmpty()) {
            User user = new User();
            user.setId(userId);
            user.setName(userName);
            //сразу добавляем в столбец каунтера 1 сообщение
            user.setMsg_number(1);

            userRepository.save(user);
            log.info("Added to DB: " + user);
        } else {
            userRepository.updateMsgNumberByUserId(userId);
        }
    }

    private void replyToUser(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
