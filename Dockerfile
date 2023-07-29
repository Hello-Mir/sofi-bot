# Используем базовый образ для Java
FROM openjdk:17-jdk

# Устанавливаем переменные окружения
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/sofi
ENV SPRING_DATASOURCE_USERNAME=tsar
ENV SPRING_DATASOURCE_PASSWORD=gvidon

# Копируем скомпилированный JAR-файл в контейнер
COPY target/telegrambot.jar /bot.jar

# Открываем порт, на котором будет работать приложение
EXPOSE 8080

# Запускаем приложение при старте контейнера
CMD ["java", "-jar", "/bot.jar"]