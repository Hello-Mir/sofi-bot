package com.nemo.telegrambot.database;

import com.nemo.telegrambot.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    @Transactional
    @Modifying
    @Query(value = "update tg_data t set t.msg_number = t.msg_number + 1 where t.id is not null and t.id = :id")
    void updateMsgNumberByUserId(@Param("id") long id);
}
