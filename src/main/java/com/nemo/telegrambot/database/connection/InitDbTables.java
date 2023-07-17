package com.nemo.telegrambot.database.connection;

import org.springframework.stereotype.Component;

@Component
public class InitDbTables {
    private final Sql2OConnect sql2OConnect;

    public InitDbTables(Sql2OConnect sql2OConnect) {
        this.sql2OConnect = sql2OConnect;
    }

}
