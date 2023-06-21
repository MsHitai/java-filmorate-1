package ru.yandex.practicum.filmorate.storage.dao.film;

import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.Storage;

import java.util.List;
import java.util.Set;

public interface FilmStorage extends Storage<Film> {
    void addGenres(long filmId, Set<Genre> genres);

    void updateGenres(long filmId, Set<Genre> genres);

    Set<Genre> findGenres(long filmId);

    void addDirectors(long filmId, Set<Director> directors);

    void updateDirectors(long filmId, Set<Director> directors);

    Set<Director> findDirectors(long filmId);

    List<Film> getAllDirectorFilms(long dirId);
}
