package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.validator.interfaces.ValidDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NotEmpty(message = "Название не должно быть пустым")
    private String name;
    @Size(max = 200, message = "Максимальная длина описания 200 символов")
    private String description;
    @PastOrPresent(message = "Дата не должна быть в будущем")
    @ValidDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private int duration;

}
