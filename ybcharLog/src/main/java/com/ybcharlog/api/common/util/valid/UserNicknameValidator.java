package com.ybcharlog.api.common.util.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserNicknameValidator implements ConstraintValidator<UserNicknameValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Pattern pattern = Pattern.compile(
            "^[a-zA-Z가-힣0-9_.]{2,20}$");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
