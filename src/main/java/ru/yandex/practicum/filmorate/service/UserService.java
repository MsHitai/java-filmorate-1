package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ErrorUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User addNewFriend(Long id, Long friendId) {
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(id);

        return user;
    }

    public User removeFriend(Long id, Long friendId) {
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);

        if (!user.getFriends().contains(friendId)) {
            log.error("Друга с таким id нет {}", friendId);
            throw new ErrorUserException("Друга с таким id нет");
        }
        user.getFriends().remove(friendId);
        friend.getFriends().remove(id);
        return user;
    }

    public List<User> getFriends(Long id) {
        List<User> friends = new ArrayList<>();

        for (Long friendId : userStorage.getUser(id).getFriends()) {
            User friend = userStorage.getUser(friendId);
            friends.add(friend);
        }
        return friends;
    }

    public Set<User> getMutualFriends(Long id, Long otherId) {
        Set<User> mutualFriends = new HashSet<>(getFriends(id));

        mutualFriends.addAll(getFriends(otherId));
        mutualFriends.remove(userStorage.getUser(id));
        mutualFriends.remove(userStorage.getUser(otherId));
        return mutualFriends;
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }
}
