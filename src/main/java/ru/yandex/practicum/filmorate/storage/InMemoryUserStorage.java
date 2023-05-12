package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.LoginAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int newId = 0;

    @Override
    public List<User> findAll() {
        log.debug("Количество пользователей: {}", users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
        user.setId(++newId);
        checkLogin(user);
        users.put(user.getId(), user);
        log.debug("Добавлен пользователь: {}", user);
        return user;
    }

    @Override
    public User update(User user) {
        checkLogin(user);
        users.put(user.getId(), user);
        log.debug("Данные пользователя обновлены: {}", user);
        return user;
    }

    private void checkLogin(User user) {
        for (User user1 : users.values()) {
            if (user1.getLogin().equals(user.getLogin()) && user1.getId() != user.getId()) {
                log.error("Пользователь с таким login уже есть {}", user);
                throw new LoginAlreadyExistException("Пользователь с таким login уже есть!");
            }
        }
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(users.get(id));
    }
}
