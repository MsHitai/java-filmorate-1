package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.validator.interfaces.NoSpace;

import java.time.LocalDate;
@Data
public class User {
    private int id;
    @NotEmpty(message = "Email не должен быть пустой")
    @Email(message = "Не валидный Email")
    private String email;
    @NotBlank(message = "Не должен быть пустой")
    @NoSpace
    private String login;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Дата не должна быть в будущем")
    private LocalDate birthday;

}
