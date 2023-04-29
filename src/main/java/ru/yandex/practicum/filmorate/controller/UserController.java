package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.exception.ErrorUserException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
   private final Map<Integer, User> users = new HashMap<>();
   private int id = 0;

   @GetMapping
    public Collection<User> findAll() {
       log.debug("Количество пользователей: " + users.size());
       return users.values();
   }

   @PostMapping
    public User create(@Valid @RequestBody User user) throws ErrorUserException {
       if (users.containsKey(user.getId())) {
           log.error("Пользователь с таким id уже есть " + user.toString());
           throw new ErrorUserException("Пользователь с таким id уже есть!");
       }
       if (user.getName() == null) {
          user.setName(user.getLogin());
       }
       user.setId(++id);
       users.put(user.getId(), user);
       log.debug("Добавлен пользователь: " + user.toString());
       return user;
   }

   @PutMapping
   public User update(@Valid @RequestBody User user) throws ErrorUserException {
      if (!users.containsKey(user.getId())) {
         log.error("Пользователя с таким id нет " + user.toString());
         throw new ErrorUserException("Пользователя с таким id нет");
      }
      users.put(user.getId(), user);
      log.debug("Данные пользователя обновлены: " + user.toString());
      return user;
   }
}
