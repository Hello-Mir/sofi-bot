package com.nemo.telegrambot;

import com.nemo.telegrambot.components.Buttons;
import com.nemo.telegrambot.config.BotConfig;
import com.nemo.telegrambot.database.MessageRepository;
import com.nemo.telegrambot.database.UserRepository;
import com.nemo.telegrambot.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class GptBot extends TelegramLongPollingBot {
    private static final String BOT_REPLY_TXT = "Запрос пользователя обработан";
    private final BotConfig config;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public GptBot(BotConfig config, UserRepository userRepository, MessageRepository messageRepository) {
        this.config = config;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
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
            case "/start":
                startBot(chatId, userName);
                break;
            case "/ask":
                sendMessageToGPT(chatId, userName, receivedMessage);
                break;
            case "/delete_all_my_questions":
                sendDeleteAllMyMessagesText(chatId);
                break;
            case "/help":
                sendHelpText(chatId);
                break;
            default:
                break;
        }
    }

    private void startBot(long chatId, String userName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Здравствуйте, " + userName + "Я Софи - Ваш помощник");
        message.setReplyMarkup(Buttons.inlineMarkup());

        try {
            execute(message);
            log.info(BOT_REPLY_TXT);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void sendHelpText(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(com.nemo.telegrambot.components.BotCommands.HELP_TEXT);

        try {
            execute(message);
            log.info(BOT_REPLY_TXT);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void sendMessageToGPT(long chatId, String username, String text) {
//        SendMessage message = new SendMessage();
//        message.setChatId(chatId);
//        message.setText(BotCommands.DELETE_ALL_MY_MESSAGES_TEXT);

        //            execute(message);
        messageRepository.saveMessage(text, chatId);
        log.info("USER question saved");
    }

    private void sendDeleteAllMyMessagesText(long chatId) {
        messageRepository.deleteAllMessagesForUser(chatId);
        log.info("All USER questions where DELETED");
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
}
