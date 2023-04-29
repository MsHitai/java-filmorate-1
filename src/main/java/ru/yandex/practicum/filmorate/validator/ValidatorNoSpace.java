package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.validator.interfaces.NoSpace;

public class ValidatorNoSpace implements ConstraintValidator<NoSpace, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        return s.matches("\\S+");
    }
}
