package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ErrorFilmException;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private Long newId = 0L;

    @Override
    public List<Film> findAll() {
        log.debug("Количество фильмов: {}", films.size());
        return new ArrayList<>(films.values());
    }

    @Override
    public Film create(Film film) {
        if (films.containsKey(film.getId())) {
            log.error("Такой фильм уже есть в базе данных");
            throw new FilmAlreadyExistException("Такой фильм уже есть в базе данных");
        }
        film.setId(++newId);
        films.put(film.getId(), film);
        log.debug("Добавлен новый фильм: {}", film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            log.error("Такого фильма нет в базе данных");
            throw new ErrorFilmException("Такого фильма нет в базе данных");
        }
        films.put(film.getId(), film);
        log.debug("Информация о фильме обновлена: {}", film);
        return film;
    }

    @Override
    public Film getFilm(Long filmId) {
        if (!films.containsKey(filmId)) {
            log.error("Такого фильма нет в базе данных");
            throw new ErrorFilmException("Такого фильма нет в базе данных");
        }
        return films.get(filmId);
    }
}
