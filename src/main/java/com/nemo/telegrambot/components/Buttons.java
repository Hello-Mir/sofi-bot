package com.nemo.telegrambot.components;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class Buttons {
    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("Старт");
    private static final InlineKeyboardButton DELETE_ALL_QUESTIONS_BUTTON = new InlineKeyboardButton("Удалить вопросы");
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("Помощь");

    public static InlineKeyboardMarkup inlineMarkup() {
        START_BUTTON.setCallbackData("/start");
        DELETE_ALL_QUESTIONS_BUTTON.setCallbackData("/delete");
        HELP_BUTTON.setCallbackData("/help");

        List<InlineKeyboardButton> rowInline =
                List.of(START_BUTTON, DELETE_ALL_QUESTIONS_BUTTON, HELP_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
}
