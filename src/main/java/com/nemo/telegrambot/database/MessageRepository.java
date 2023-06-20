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
    @Query(value = "insert into messages (body, user_id, created_at) values(:body,:userId,current_timestamp)", nativeQuery = true)
    void saveMessage(@Param("body") String body, @Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(value = "delete from messages where id=:id", nativeQuery = true)
    void deleteMessageById(@Param("id") Long messageId);

    @Transactional
    @Modifying
    @Query(value = "delete from messages where user_id =:id", nativeQuery = true)
    void deleteAllMessagesForUser(@Param("id") Long userId);
}
