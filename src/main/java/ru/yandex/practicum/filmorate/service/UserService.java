package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ErrorUserException;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.events.EventsDao;
import ru.yandex.practicum.filmorate.storage.dao.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.dao.friends.FriendsDao;
import ru.yandex.practicum.filmorate.storage.dao.like.LikeDao;
import ru.yandex.practicum.filmorate.storage.dao.ratingMpa.RatingMpaDao;
import ru.yandex.practicum.filmorate.storage.dao.user.UserStorage;
import static ru.yandex.practicum.filmorate.service.EventsService.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    private final FriendsDao friendsDao;
    private final LikeDao likeDao;
    private final FilmStorage filmStorage;
    private final EventsDao eventsDao;
    private final EventsService eventsService;
    private final RatingMpaDao ratingMpaDao;

    public void addNewFriend(Long id, Long friendId) {
        contains(id);
        contains(friendId);
        boolean isMutual = friendsDao.get(friendId, id) != null;
        friendsDao.add(id, friendId, isMutual);
        eventsService.addEvent(id, friendId, FRIEND_TYPE, ADD_OPERATION);
    }

    public void removeFriend(Long id, Long friendId) {
        friendsDao.delete(id, friendId);
        eventsService.addEvent(id, friendId, FRIEND_TYPE, REMOVE_OPERATION);
    }

    public List<User> getFriends(Long id) {
        contains(id);
        return friendsDao.getFriends(id).stream()
                .mapToLong(Long::valueOf)
                .mapToObj(this::findById)
                .collect(Collectors.toList());
    }

    public List<User> getMutualFriends(Long id, Long otherId) {
        contains(id);
        contains(otherId);
        List<User> friendsOfOtherId = new ArrayList<>(getFriends(otherId));
        return getFriends(id).stream()
                .filter(friendsOfOtherId::contains)
                .collect(Collectors.toList());
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        makeNameALoginIfNameIsEmpty(user);
        return userStorage.create(user);
    }

    public User update(User user) {
        contains(user.getId());
        makeNameALoginIfNameIsEmpty(user);
        return userStorage.update(user);
    }

    public User findById(long id) {
        contains(id);
        return userStorage.findById(id);
    }

    public List<Event> findFeedEvents(long userId) {
        contains(userId);
        return eventsDao.getUserEvents(userId);
    }

    public List<Film> getRecommendFilms(long userId) {
        List<Long> users = getIntersectionList(userId);
        List<Long> userIdFilms = getLikedFilmsForUser(userId);
        List<Film> films = new ArrayList<>();
        users.forEach(id -> getLikedFilmsForUser(id).stream()
                .mapToLong(filmId -> filmId)
                .filter(filmId -> !userIdFilms.contains(filmId))
                .forEachOrdered(filmId -> {
                    userIdFilms.add(filmId);
                    Film film = filmStorage.findById(filmId);
                    film.setGenres(filmStorage.findGenres(filmId));
                    film.setMpa(ratingMpaDao.findById(film.getMpa().getId()));
                    films.add(film);
                }));
        return films;
    }

    private void makeNameALoginIfNameIsEmpty(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    private void contains(long userId) {
        try {
            userStorage.findById(userId);
        } catch (EmptyResultDataAccessException exception) {
            log.debug("Пользователь с id {} не найден", userId);
            throw new ErrorUserException("Пользователь не найден");
        }
    }

    public void deleteUser(long id) {
        contains(id);
        userStorage.delete(id);
    }

    private List<Long> getLikedFilmsForUser(long id) {
        return likeDao.getFilmIdLikes(id);
    }

    private Integer getIntersection(long userId, long anotherUserId) {
        List<Long> intersection = getLikedFilmsForUser(anotherUserId).stream()
                .mapToLong(id -> id)
                .filter(id -> getLikedFilmsForUser(userId).contains(id))
                .boxed()
                .collect(Collectors.toList());
        return intersection.size();
    }

    private List<Long> getIntersectionList(long userId) {
        return userStorage.findAll().stream()
                .map(User::getId)
                .filter(id -> id != userId)
                .sorted((id1, id2) -> getIntersection(userId, id1) - getIntersection(userId, id2))
                .collect(Collectors.toList());
    }
}