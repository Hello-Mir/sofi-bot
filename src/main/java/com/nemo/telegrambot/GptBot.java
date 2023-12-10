package com.nemo.telegrambot;

import com.nemo.telegrambot.components.BotCommands;
import com.nemo.telegrambot.components.Buttons;
import com.nemo.telegrambot.config.BotConfig;
import com.nemo.telegrambot.database.UserRepository;
import com.nemo.telegrambot.database.UserRequestsRepository;
import com.nemo.telegrambot.model.User;
import com.nemo.telegrambot.model.freegpt.FreeGptRequest;
import com.nemo.telegrambot.model.freegpt.Model;
import com.nemo.telegrambot.service.UserRequestBuilder;
import com.nemo.telegrambot.service.FreeGptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.nemo.telegrambot.components.BotCommands.EXCEPTION_PRETEXT;
import static com.nemo.telegrambot.components.BotCommands.START_TEXT;
import static java.util.UUID.randomUUID;

@Slf4j
@Component
public class GptBot extends TelegramLongPollingBot {
    private final BotConfig config;
    private final UserRepository userRepository;
    private final UserRequestsRepository userRequestsRepository;
    private final UserRequestBuilder requestBuilder;

    private final FreeGptService freeGptService;

    public GptBot(BotConfig config, UserRepository userRepository,
            UserRequestsRepository userRequestsRepository, UserRequestBuilder requestBuilder,
            FreeGptService freeGptService) {
        this.config = config;
        this.userRepository = userRepository;
        this.userRequestsRepository = userRequestsRepository;
        this.requestBuilder = requestBuilder;
        this.freeGptService = freeGptService;
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
            case "/delete" -> deleteDialogue(chatId);
            case "/help" -> sendHelpText(chatId);
            default -> sendMessageToGPT(chatId, receivedMessage);
        }
    }

    private void startBot(long chatId, String userName) {
        String startText = "Приветствую, " + userName + "\uD83D\uDC8B" + ".\n" + START_TEXT;
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
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        try {
            String conversationId = getConversationId(chatId);
            FreeGptRequest freeGptRequest = requestBuilder.buildFreeGptRequestld(conversationId, Model.GPT_3_5_TURBO,
                    "default", text);
            String body = freeGptService.sendRequest("text/event-stream", freeGptRequest);
            log.info(String.format("Received request from user: %s", freeGptRequest));

            userRequestsRepository.saveUserRequestData(conversationId, chatId);
            log.info(String.format("Sent message to user: %s", message));

            message.setText(body);
            replyToUser(message);
        } catch (Exception exception) {
            String exceptionMessage = exception.getMessage();
            message.setText(EXCEPTION_PRETEXT + exceptionMessage);
            replyToUser(message);
        }

    }

    private void deleteDialogue(long chatId) {
        userRequestsRepository.deleteCurrentDialogue(chatId);
        SendMessage message = new SendMessage();
        message.setText("Диалог успешно удален.");
        message.setChatId(chatId);
        replyToUser(message);
    }

    private void updateDB(long userId, String userName) {
        if (userRepository.findById(userId).isEmpty()) {
            User user = new User();
            user.setId(userId);
            user.setName(userName);
            // сразу добавляем в столбец каунтера 1 сообщение
            user.setMsg_number(1);

            userRepository.save(user);
            log.info(String.format("User: '%s' added to DB", user));
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

    private String getConversationId(Long userId) {
        String conversationId = userRequestsRepository.getConversationId(userId);
        log.info("Got conversationId for user:" + conversationId);
        return conversationId == null ? randomUUID().toString() : conversationId;
    }
}
