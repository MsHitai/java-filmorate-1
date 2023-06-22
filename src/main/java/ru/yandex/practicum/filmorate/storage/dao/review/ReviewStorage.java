package ru.yandex.practicum.filmorate.storage.dao.review;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewStorage {

    Review add(Review review);

    Review update(Review review);

    List<Review> getAllReviews();

    List<Review> getAllReviewsByFilm(Long filmId);

    boolean delete(Long id);

    Review get(Long id);

    boolean addLike(Long id, Long userId);

    boolean addDislike(Long id, Long userId);

    boolean deleteLike(Long id, Long userId);

    boolean deleteDislike(Long id, Long userId);
}
