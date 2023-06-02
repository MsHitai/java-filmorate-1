package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.validation.interfaces.NoSpace;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@Builder
@Validated
public class User {
    private long id;
    @NotBlank(message = "Email не должен быть пустой")
    @Email(message = "Не валидный Email")
    private String email;
    @NotBlank(message = "Не должен быть пустой")
    @NoSpace
    private String login;
    private String name;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Дата не должна быть в будущем")
    private LocalDate birthday;
}
