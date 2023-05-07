package com.ybcharlog.api.Common.util;

import org.springframework.core.env.Environment;

public class ProfileUtil {

    private static Environment ENV;

    public ProfileUtil(Environment env) {
        ProfileUtil.ENV = env;
    }

    public static String getActiveProfile() {
        return ENV.getActiveProfiles()[0];
    }

    public static boolean isProdProfile() {
        for (String profile : ENV.getActiveProfiles()) {
            if (profile.equals("prod")) {
                return true;
            }
        }
        return false;
    }
}
