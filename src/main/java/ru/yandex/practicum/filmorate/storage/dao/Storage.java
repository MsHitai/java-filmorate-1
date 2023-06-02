package ru.yandex.practicum.filmorate.storage.dao;

import java.util.List;
import java.util.Optional;

public interface Storage<T> {
    List<T> findAll();

    T create(T too);

    T update(T too);

    T findById(long id);

}
