package ru.yandex.practicum.filmorate.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private UserController controller;
    private User user;

    @Test
    public void shouldCreateNewUserIfValidData() {
        user = User.builder()
                .login("login")
                .name("name")
                .email("email@email.ru")
                .birthday(LocalDate.of(2000, 1, 11))
                .build();

        User user1 = controller.create(user);

        assertEquals(user, user1);
    }

    @Test
    public void shouldThrowExceptionIfInvalidEmail() {
        user = User.builder()
                .id(1)
                .login("login")
                .name("name")
                .email("emailemail.ru")
                .birthday(LocalDate.of(2000, 1, 11))
                .build();

        assertThrows(ConstraintViolationException.class, () -> controller.create(user));
    }

    @Test
    public void shouldThrowExceptionIfEmptyEmail() {
        user = User.builder()
                .id(1)
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2000, 1, 11))
                .build();

        assertThrows(ConstraintViolationException.class, () -> controller.create(user));
    }

    @Test
    public void shouldThrowExceptionIfLoginContainsSpace() {
        user = User.builder()
                .id(1)
                .login("lo gin")
                .name("name")
                .email("email@email.ru")
                .birthday(LocalDate.of(2000, 1, 11))
                .build();

        assertThrows(ConstraintViolationException.class, () -> controller.create(user));
    }

    @Test
    public void shouldThrowExceptionIfEmptyLogin() {
        user = User.builder()
                .id(1)
                .login("")
                .name("name")
                .email("email@email.ru")
                .birthday(LocalDate.of(2000, 1, 11))
                .build();

        assertThrows(ConstraintViolationException.class, () -> controller.create(user));
    }

    @Test
    public void shouldUseLoginIfNameIsEmpty() {
        user = User.builder()
                .id(1)
                .login("login")
                .name("")
                .email("email@email.ru")
                .birthday(LocalDate.of(2000, 1, 11))
                .build();

        controller.create(user);

        assertEquals("login", user.getName());
    }

    @Test
    public void shouldThrowExceptionIfBirthdayInFuture() {
        user = User.builder()
                .id(1)
                .login("login")
                .name("name")
                .email("email@email.ru")
                .birthday(LocalDate.of(2222, 1, 11))
                .build();

        assertThrows(ConstraintViolationException.class, () -> controller.create(user));
    }
}