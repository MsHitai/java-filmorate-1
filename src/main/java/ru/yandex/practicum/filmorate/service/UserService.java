package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ErrorUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User addNewFriend(int id, int friendId) {
        User user = findById(id);
        User friend = findById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(id);

        return user;
    }

    public User removeFriend(int id, int friendId) {
        User user = findById(id);
        User friend = findById(friendId);

        if (!user.getFriends().remove(friendId)) {
            log.error("Друга с таким id нет {}", friendId);
            throw new ErrorUserException("Друга с таким id нет");
        }
        friend.getFriends().remove(id);
        return user;
    }

    public List<User> getFriends(int id) {
        return findById(id).getFriends().stream()
                .map(this::findById)
                .collect(Collectors.toList());
    }

    public List<User> getMutualFriends(int id, int otherId) {
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
        findById(user.getId());
        makeNameALoginIfNameIsEmpty(user);
        return userStorage.update(user);
    }

    public User findById(int id) {
       return userStorage.findById(id)
               .orElseThrow(() -> new ErrorUserException("Пользователя с таким id нет"));
    }

    private void makeNameALoginIfNameIsEmpty(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
