package com.nemo.telegrambot.database.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.sql2o.Sql2o;

@Configuration
public class Sql2OConnect {
    @Value("${spring.datasource.url}")
    private String dataBase;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;

    public Sql2o createSql() {
        return new Sql2o(dataBase, userName, password);
    }
}
