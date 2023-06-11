package com.nemo.telegrambot.components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "start bot"),
            new BotCommand("/help", "bot info")
    );

    String HELP_TEXT = "Этот бот принимает запросы и передает в том же виде ChatGpt.\n" +
            "Доступны следующие команды:\n" +
            "/start - запуск бота\n" +
            "/help - меню помощи";
}
