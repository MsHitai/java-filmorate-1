package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class Review {

    @NotNull
    private long reviewId;

    @NotNull
    private String content;

    @NotNull
    private Boolean isPositive;

    private Long userId;

    private Long filmId;

    @NotNull
    private Integer useful;

}
