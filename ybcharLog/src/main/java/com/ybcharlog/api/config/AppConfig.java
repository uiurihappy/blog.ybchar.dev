package com.ybcharlog.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "auth")
public class AppConfig {

    public YbcharConfig ybcharConfig;

    @Data
    public static class YbcharConfig {
        public String key;
        public String secretKey;
    }
}
