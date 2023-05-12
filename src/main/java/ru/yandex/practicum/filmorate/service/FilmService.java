package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ErrorFilmException;
import ru.yandex.practicum.filmorate.exception.ErrorUserException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;

    public Film addNewLike(int filmId, int userId) {
        Film film = findById(filmId);

        film.getLikes().add(userId);
        return film;
    }

    public Film removeLike(int filmId, int userId) {
        Film film = findById(filmId);

        if (!film.getLikes().contains(userId)) {
            log.error("Пользователя с таким id нет {}", userId);
            throw new ErrorUserException("Пользователя с таким id нет");
        }
        film.getLikes().remove(userId);
        return film;
    }

    public List<Film> getTopPopularFilms(int count) {
        return filmStorage.findAll().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        findById(film.getId());
        return filmStorage.update(film);
    }

    public Film findById(int filmId) {
        return filmStorage.findById(filmId)
                .orElseThrow(() -> new ErrorFilmException("Такого фильма нет в базе данных"));
    }
}
