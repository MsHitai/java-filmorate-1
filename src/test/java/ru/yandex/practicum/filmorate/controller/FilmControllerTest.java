package ru.yandex.practicum.filmorate.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

import static org.apache.logging.log4j.util.Strings.repeat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmControllerTest {
    @Autowired
    private FilmController controller;

    @Test
    public void shouldCreateNewFilmIfValidData() {
        Film film = Film.builder()
                .id(1)
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(2000, 1, 11))
                .duration(60)
                .build();

        Film film1 = controller.create(film);

        assertEquals(film, film1);
    }

    @Test
    public void shouldThrowExceptionIfNameIsEmpty() {
        Film film = Film.builder()
                .id(1)
                .name("")
                .description("description")
                .releaseDate(LocalDate.of(2000, 1, 11))
                .duration(60)
                .build();

        assertThrows(ConstraintViolationException.class, () -> controller.create(film));
    }

    @Test
    public void shouldThrowExceptionIfDescriptionLength201() {
        String description = repeat("f", 201);
        Film film = Film.builder()
                .id(1)
                .name("name")
                .description(description)
                .releaseDate(LocalDate.of(2000, 1, 11))
                .duration(60)
                .build();

        assertThrows(ConstraintViolationException.class, () -> controller.create(film));
    }

    @Test
    public void shouldThrowExceptionIfReleaseDateEarlier1895December28() {
        Film film = Film.builder()
                .id(1)
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(1895, 12, 27))
                .duration(60)
                .build();

        assertThrows(ConstraintViolationException.class, () -> controller.create(film));
    }

    @Test
    public void shouldThrowExceptionIfDurationIsNegative() {
        Film film = Film.builder()
                .id(1)
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(2000, 1, 11))
                .duration(-60)
                .build();

        assertThrows(ConstraintViolationException.class, () -> controller.create(film));
    }
}