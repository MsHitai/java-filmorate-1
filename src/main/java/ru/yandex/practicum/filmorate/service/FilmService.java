package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    public Film addNewLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilm(filmId);

        film.getLikes().add(userId);
        return film;
    }

    public Film removeLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilm(filmId);

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

    public FilmStorage getFilmStorage() {
        return filmStorage;
    }

}
