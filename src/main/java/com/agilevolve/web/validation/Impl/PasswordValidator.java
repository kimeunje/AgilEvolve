package com.agilevolve.web.validation.Impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

import com.agilevolve.web.validation.ContainsSpecialCharacter;

public class PasswordValidator implements ConstraintValidator<ContainsSpecialCharacter, String> {
    private static final String SPECIAL_CHARACTER_REGEX = "^(?=.*[!@#$%^&*()_+]).*";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(SPECIAL_CHARACTER_REGEX);
        return pattern.matcher(password).matches();
    }
}