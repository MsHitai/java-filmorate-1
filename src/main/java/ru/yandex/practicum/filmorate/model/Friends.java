package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Friends {
    @NotNull
    private long userId;
    @NotNull
    private long friendId;
    @NotNull
    private boolean isMutual;
}
