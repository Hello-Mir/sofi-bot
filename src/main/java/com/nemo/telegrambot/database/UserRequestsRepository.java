package com.nemo.telegrambot.database;

import com.nemo.telegrambot.model.UserRequests;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRequestsRepository extends CrudRepository<UserRequests, Long> {
    @Transactional
    @Modifying
    @Query(value = "insert into user_requests (conversation_id, user_id, created_at) " +
            "values(:conversationId,:userId,CURRENT_TIMESTAMP)", nativeQuery = true)
    void saveUserRequestData(@Param("conversationId") String conversationId, @Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(value = "delete from user_requests where user_id =:userId", nativeQuery = true)
    void deleteCurrentDialogue(@Param("userId") Long userId);

    @Transactional
    @Query(value = "select conversation_id from user_requests where user_id = :userId", nativeQuery = true)
    String getConversationId(@Param("userId") Long userId);
}
