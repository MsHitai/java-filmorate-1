package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.validator.interfaces.ValidDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Validated
public class Film {
    private int id;
    @JsonIgnore
    private final Set<Integer> likes = new HashSet<>();
    @NotBlank(message = "Название не должно быть пустым")
    private String name;
    @NotNull
    @Size(max = 200, message = "Максимальная длина описания 200 символов")
    private String description;
    @ValidDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private int duration;

    public Set<Integer> getLikes() {
        return likes;
    }
}
