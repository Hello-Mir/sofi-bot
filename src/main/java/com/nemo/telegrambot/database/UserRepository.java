package com.nemo.telegrambot.database;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nemo.telegrambot.model.freegpt.User;

public interface UserRepository extends CrudRepository<User, Long> {

    @Transactional
    @Modifying
    @Query(value = "update users u set u.msg_number = u.msg_number + 1 where u.id is not null and u.id = :id")
    void updateMsgNumberByUserId(@Param("id") long id);
}
