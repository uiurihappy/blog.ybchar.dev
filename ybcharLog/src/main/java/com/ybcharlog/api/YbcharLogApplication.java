package com.ybcharlog.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YbcharLogApplication {

    /* 프론트엔드 구상
        SSR -> jsp, thymeleaf, mustache, freemarker
             -> html rendering
        SPA -> vue -> javascript <-> API(JSON) (SSR로 가면 nuxt)
            -> react -> typescript <-> API (SSR로 가면 next)
     */
    public static void main(String[] args) {
        SpringApplication.run(YbcharLogApplication.class, args);
    }

}
