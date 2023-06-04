package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class Like {
    @NotNull
    private long filmId;
    @NotNull
    private long userId;
}
