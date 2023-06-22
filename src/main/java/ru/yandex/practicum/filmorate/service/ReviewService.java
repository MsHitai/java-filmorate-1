package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.dao.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.dao.review.ReviewStorage;
import ru.yandex.practicum.filmorate.storage.dao.user.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewStorage reviewStorage;
    private final UserService userService;
    private final FilmService filmService;

    public Review add(Review review) {
        userService.findById(review.getUserId());
        filmService.findById(review.getFilmId());
        return reviewStorage.add(review);
    }

    public Review get(Long id) {
        return reviewStorage.get(id);
    }

    public List<Review> getReviews(Long filmId, Long count) {
        if (filmId == null) {
            return reviewStorage.getAllReviews().stream()
                    .sorted(Comparator.comparingLong(Review::getUseful).reversed())
                    .limit(count)
                    .collect(Collectors.toList());
        }
        return reviewStorage.getAllReviewsByFilm(filmId).stream()
                .sorted(Comparator.comparingLong(Review::getUseful).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    public Review update(Review review) {
        return reviewStorage.update(review);
    }

    public boolean delete(Long id) {
        return reviewStorage.delete(id);
    }

    public boolean addLike(Long id, Long userId) {
        userService.findById(userId);
        return reviewStorage.addLike(id, userId);
    }

    public boolean addDislike(Long id, Long userId) {
        return reviewStorage.addDislike(id, userId);
    }

    public boolean deleteLike(Long id, Long userId) {
        return reviewStorage.deleteLike(id, userId);
    }

    public boolean deleteDislike(Long id, Long userId) {
        return reviewStorage.deleteDislike(id, userId);
    }
}
