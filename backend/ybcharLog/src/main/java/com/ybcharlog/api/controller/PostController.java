package com.ybcharlog.api.controller;

import com.ybcharlog.api.RequestDto.PostCreateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
public class PostController {

    /* HTTP methods
     * GET, POST, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
     * 글 등록 - POST
     */

    @PostMapping("/posts")
    public String post(
//            @RequestParam Map<String, String> params
            @RequestBody PostCreateDto params
            ) {
//        System.out.println("title = " + title);
//        System.out.println("content = " + content);
//        log.info("title={}, content={}", title, content);
        log.info("params = {}", params.toString());
        return "Hello world!";
    }

}
