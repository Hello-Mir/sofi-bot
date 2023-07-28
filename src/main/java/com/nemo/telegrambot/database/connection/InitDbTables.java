package com.nemo.telegrambot.database.connection;

import org.springframework.stereotype.Component;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

@Component
public class InitDbTables {
    private final Sql2OConnect sql2OConnect;

    public InitDbTables(Sql2OConnect sql2OConnect) {
        this.sql2OConnect = sql2OConnect;
        initTables();
    }


    public void initTables() {
        Sql2o connection = sql2OConnect.createSql();
        try (Connection open = connection.open()) {
            createUserTable(open);
            createUserRequests(open);
        }
    }

    private void createUserTable(Connection connection) {
        String queryForUserTable = """
                create table IF NOT EXISTS users
                (
                    id bigint not null primary key,
                    msg_number integer not null,
                    name varchar(255)
                )
                """;
        Query query = connection.createQuery(queryForUserTable);
        query.executeUpdate();
    }

    private void createUserRequests(Connection connection) {
        String queryText = """
                    create table IF NOT EXISTS user_requests
                    (id serial primary key, conversation_id varchar(50), user_id bigint
                    constraint fk_user_request_user references users on delete cascade,
                    created_at timestamp
                    )
                """;
        Query query = connection.createQuery(queryText);
        query.executeUpdate();
    }

}
