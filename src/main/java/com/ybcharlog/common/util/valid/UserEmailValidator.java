package com.ybcharlog.common.util.valid;

import com.google.common.base.Strings;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserEmailValidator implements ConstraintValidator<UserEmailValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!this.isValidTextLength(value, 1, 40)) {
            return false;
        }

        Pattern pattern = Pattern.compile(
            "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    private boolean isValidTextLength(String text, int minLength, int maxLength) {
        if (Strings.isNullOrEmpty(text)) {
            return minLength == 0;
        }
        return text.length() >= minLength && text.length() <= maxLength;
    }
}
