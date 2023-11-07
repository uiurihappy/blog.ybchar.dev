package com.ybcharlog.security;

import com.ybcharlog.common.util.EncryptUtil;
import com.ybcharlog.exception.FailEncryptException;

public class EncryptService {

    public static String encryptPhoneNumber(String phoneNumber) throws FailEncryptException {
        if (phoneNumber == null) {
            return "";
        }

        try {
            return EncryptUtil.encryptAES256(phoneNumber.replaceAll("-", "").trim());
        } catch (Exception e) {
            throw new FailEncryptException(
                String.format("%s(%s)", "Fail AES256 Encrypt.", phoneNumber));
        }
    }

    public static String decryptPhoneNumber(String phoneNumber) throws FailEncryptException {
        if (phoneNumber == null) {
            return "";
        }

        try {
            return EncryptUtil.decryptAES256(phoneNumber);
        } catch (Exception e) {
            return phoneNumber;
        }
    }
}
