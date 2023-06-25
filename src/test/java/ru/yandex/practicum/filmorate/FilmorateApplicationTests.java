package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {

    private final UserService userService;

    private final FilmService filmService;

    private Film film;
    private User user;

    @BeforeEach
    public void setUp() {
        film = Film.builder()
                .id(1)
                .name("SomeTestFilm")
                .description("SomeTestDescription")
                .releaseDate(LocalDate.of(2000, 1, 11))
                .duration(110)
                .mpa(new Mpa(1, "G"))
                .build();

        user = User.builder()
                .id(1)
                .name("SomeUser")
                .login("SomeLogin")
                .email("login@mail.ru")
                .birthday(LocalDate.of(1990, Month.DECEMBER, 5))
                .build();
    }

    @Test
    void contextLoads() {
    }

}
