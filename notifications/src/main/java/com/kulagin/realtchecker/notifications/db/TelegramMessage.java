package com.kulagin.realtchecker.notifications.db;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class TelegramMessage {
    @Id
    private Integer id;
    @Indexed
    private Instant date;
}
