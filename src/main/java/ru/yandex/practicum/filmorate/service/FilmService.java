package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ErrorFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.dao.like.LikeDao;
import ru.yandex.practicum.filmorate.storage.dao.ratingMpa.RatingMpaDao;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final RatingMpaDao ratingMpa;
    private final LikeDao likeDao;
    private final UserService userService;

    public void addNewLike(long filmId, long userId) {
        contains(filmId);
        userService.findById(userId);
        likeDao.add(filmId, userId);
    }

    public void removeLike(long filmId, long userId) {
        contains(filmId);
        userService.findById(userId);
        likeDao.delete(filmId, userId);
    }

    public List<Film> getTopPopularFilms(int count) {
        List<Film> result = filmStorage.findAll().stream()
                .sorted(this::likeCompare)
                .limit(count).collect(Collectors.toCollection(LinkedList::new));
        for (Film film : result) {
            film.setGenres(filmStorage.findGenres(film.getId()));
            film.setMpa(ratingMpa.findById(film.getMpa().getId()));
        }
        return result;
    }

    public List<Film> findAll() {
        List<Film> films = filmStorage.findAll();
        for (Film film : films) {
            film.setGenres(filmStorage.findGenres(film.getId()));
            film.setMpa(ratingMpa.findById(film.getMpa().getId()));
        }
        return films;
    }

    public Film create(Film film) {
        Film result = filmStorage.create(film);
        filmStorage.addGenres(result.getId(), film.getGenres());
        result.setGenres(filmStorage.findGenres(result.getId()));
        return result;
    }

    public Film update(Film film) {
        contains(film.getId());
        Film result = filmStorage.update(film);
        filmStorage.updateGenres(result.getId(), film.getGenres());
        result.setGenres(filmStorage.findGenres(result.getId()));
        result.setMpa(ratingMpa.findById(result.getMpa().getId()));
        return result;
    }

    public Film findById(long filmId) {
        Film result = contains(filmId);
        result.setGenres(filmStorage.findGenres(filmId));
        result.setMpa(ratingMpa.findById(result.getMpa().getId()));
        return result;
    }

    private int likeCompare(Film film, Film otherFilm) {
        return Integer.compare(likeDao.check(otherFilm.getId()), likeDao.check(film.getId()));
    }

    private Film contains(long filmId) {
        try {
           return filmStorage.findById(filmId);
        } catch (EmptyResultDataAccessException e) {
            log.debug("Фильм с id {} не найден", filmId);
            throw new ErrorFilmException("Фильм не найден");
        }
    }
}
