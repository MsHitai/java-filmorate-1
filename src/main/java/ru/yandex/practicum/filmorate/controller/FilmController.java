package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ErrorFilmException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;

    @GetMapping
    public Collection<Film> findAll() {
        log.debug("Количество фильмов: " + films.size());
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ErrorFilmException {
        if (films.containsKey(film.getId())) {
            log.error("Такой фильм уже есть в базе данных");
            throw new ErrorFilmException("Такой фильм уже есть в базе данных");
        }
        film.setId(++id);
        films.put(film.getId(), film);
        log.debug("Добавлен новый фильм: " + film.toString());
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ErrorFilmException {
        if (!films.containsKey(film.getId())) {
            log.error("Такого фильма нет в базе данных");
            throw new ErrorFilmException("Такого фильма нет в базе данных");
        }
        films.put(film.getId(), film);
        log.debug("Информация о фильме обновлена: " + film.toString());
        return film;
    }
}
