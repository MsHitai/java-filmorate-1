package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@Data
public class Event {
    private long eventId;
    private long timestamp;
    @NotNull
    private long userId;
    private String eventType;
    private String operation;
    @NotNull
    private long entityId;
}
