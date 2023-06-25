package ru.yandex.practicum.filmorate.storage.dao.ratingMpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface RatingMpaStorage {
    Mpa findById(int id);

    List<Mpa> findAll();
}
