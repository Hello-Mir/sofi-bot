package com.nemo.telegrambot.components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "start bot"),
            new BotCommand("без какой-нибудь команды", "передать сообщение в GPT"),
            new BotCommand("/delete", "удаление всех сообщений текущего пользователя"),
            new BotCommand("/help", "меню помощи или справки")
    );

    String HELP_TEXT = """
            Этот бот принимает запросы и передает в том же виде ChatGpt.
            Доступны следующие команды:
            /start - запуск бота
            /delete - удалить все Ваши сообщения из БД
            /help - меню помощи,
            если не указана ни одна из команд выше""";

}
