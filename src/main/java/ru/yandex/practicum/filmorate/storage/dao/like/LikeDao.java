package ru.yandex.practicum.filmorate.storage.dao.like;

public interface LikeDao {
    void add(long filmId, long userId);

    void delete(long filmId, long userId);

    int check(long filmId);
}
