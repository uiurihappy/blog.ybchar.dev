package com.ybcharlog.api.Common.util.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<PhoneValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Pattern pattern1 = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");
        Pattern pattern2 = Pattern.compile("\\d{11}");

        Matcher matcher1 = pattern1.matcher(value);
        Matcher matcher2 = pattern2.matcher(value);

        return matcher1.matches() || matcher2.matches();
    }
}
