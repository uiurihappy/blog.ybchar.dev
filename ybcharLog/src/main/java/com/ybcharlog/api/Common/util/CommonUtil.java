package com.ybcharlog.api.Common.util;

import java.util.Random;

public class CommonUtil {

    private static Random random = new Random();

    public static String generateRandomAllAlphaAndNumericString(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 90; // letter 'Z'

        // ASCII 48~57: 숫자, 65~90: 대문자 영문, 97~122: 소문자 영문
        return random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || (i >= 65 && i <= 90) || (i >= 97 && i <= 122)))
            .limit(length)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

    }
}
