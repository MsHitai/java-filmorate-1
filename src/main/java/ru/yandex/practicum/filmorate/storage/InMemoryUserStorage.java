package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ErrorUserException;
import ru.yandex.practicum.filmorate.exception.LoginAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;


@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private Long newId = 0L;

    @Override
    public List<User> findAll() {
        log.debug("Количество пользователей: {}", users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
        for (User user1 : users.values()) {
            if (user1.getLogin().equals(user.getLogin())) {
                log.error("Пользователь с таким login уже есть {}", user);
                throw new LoginAlreadyExistException("Пользователь с таким login уже есть!");
            }
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        user.setId(++newId);
        users.put(user.getId(), user);
        log.debug("Добавлен пользователь: {}", user);
        return user;
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            log.error("Пользователя с таким id нет {}", user);
            throw new ErrorUserException("Пользователя с таким id нет");
        }
        users.put(user.getId(), user);
        log.debug("Данные пользователя обновлены: {}", user);
        return user;
    }

    @Override
    public User getUser(Long id) {
        if (!users.containsKey(id)) {
            log.error("Нет пользователя с таким id {}", id);
            throw new ErrorUserException("Пользователя с таким id нет");
        }
        return users.get(id);
    }

}
