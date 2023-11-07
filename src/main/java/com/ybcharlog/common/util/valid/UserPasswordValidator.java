package com.ybcharlog.common.util.valid;

import com.google.common.base.Strings;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserPasswordValidator implements ConstraintValidator<UserPasswordValid, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        int cnt = 0;

        if (this.isValidTextLength(value, 6, 20)) {
            if (value.matches(".*\\d.*")) {
                cnt++;
            }
            if (value.matches(".*[a-z].*")) {
                cnt++;
            }
            if (value.matches(".*[A-Z].*")) {
                cnt++;
            }
            if (value.matches(".*[!@#&()_–\\[{}\\]:;'`,.?/*~$^+=<>].*")) {
                cnt++;
            }
        }

        return cnt >= 2;
    }

    private boolean isValidTextLength(String text, int minLength, int maxLength) {
        if (Strings.isNullOrEmpty(text)) {
            return minLength == 0;
        }
        return text.length() >= minLength && text.length() <= maxLength;
    }
}
