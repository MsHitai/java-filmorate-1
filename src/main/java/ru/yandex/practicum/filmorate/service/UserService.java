package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ErrorUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.friends.FriendsDao;
import ru.yandex.practicum.filmorate.storage.dao.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;
    private final FriendsDao friendsDao;

    public void addNewFriend(Long id, Long friendId) {
        contains(id);
        contains(friendId);
        boolean isMutual = friendsDao.get(friendId, id) != null;
        friendsDao.add(id, friendId, isMutual);
    }

    public void removeFriend(Long id, Long friendId) {
        friendsDao.delete(id, friendId);
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
}
