package com.nemo.telegrambot.components;

public interface BotCommands {
    String HELP_TEXT = """
            Этот бот принимает Ваши запросы и передает их в том же виде ChatGpt.
            Доступны следующие команды:
            /start - запуск бота;
            /delete - удалить все Ваши сообщения из БД;
            /help - меню помощи.""";

    String START_TEXT = """
            Я - Софи.
            Посредник между ChatGPT и Вами.
            Можете вводить Ваши запросы, а я буду передавать их языковой модели.
            Получив ответ, верну его Вам.
            Хочу заметить, что на это порой может уйти достаточно времени...
            Но я все равно верну ответ: с ошибкой выполнения или ответом от модели.
                        
            Надо добавить, что кроме ошибок в моем коде, могут быть ошибки на стороне FreeGPT.
            (проекте, собранном энтузиастами, и позволяющем бесплатно обращаться к платной модели)
            Поэтому 100% успеха не гарантируется.
                       
            Кстати, по кнопке 'Помощь' внизу можно узнать о дополнительных возможностях.
            Теперь, если готовы, просто введите Ваш запрос после этого текста""";

    String EXCEPTION_PRETEXT = """
            \nК сожалению, при выполнении произошла ошибка \uD83E\uDEE0.
            Предлагаемые действия:
            1. Вызовите '/delete', сделайте новый запрос. Если ошибка повторится, сразу переходите к пункту 2.
            2. Передайте ошибку разработчику: вызовите '/report', скопируйте ошибку и просто отправьте ее текстом.
            Ситуация будет доведена до команды поддержки.
            Ошибку исправят (но это неточно) в ближайшее обновление.
            """;
}
