package com.nemo.telegrambot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.glassfish.grizzly.http.util.TimeStamp;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity(name = "messages")
public class Message {
    @Id
    private Long id;
    private String body;
    private Long userId;
    @DateTimeFormat
    private TimestampWithTimeZoneJdbcType createdAt;
}
