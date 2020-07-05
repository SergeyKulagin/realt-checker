package com.kulagin.realtchecker.notifications.db;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.kulagin.realtchecker.core.model.Apartment;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class Apartments {
    @Id
    private String id;
    @Indexed
    private Instant date;
    private List<Apartment> apartments;
}
