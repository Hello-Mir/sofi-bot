package com.nemo.telegrambot.database;

import com.nemo.telegrambot.model.Message;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends CrudRepository<Message, Long> {
    @Transactional
    @Modifying
    @Query(value = "insert into message (body, userId, createdAt) values(:body,:userId,current_timestamp)", nativeQuery = true)
    boolean saveMessage(@Param("body") String body, @Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(value = "delete from messages where id=:id", nativeQuery = true)
    boolean deleteMessageById(@Param("id") Long messageId);
}
