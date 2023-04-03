package com.ybcharlog.api.controller;

import com.ybcharlog.api.RequestDto.PostCreateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PostController {

    /* HTTP methods
     * GET, POST, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
     * 글 등록 - POST
     */
    /* 데이터를 검증하는 이유 (Why?)
            1. 클라이언트 개발자가 깜빡할 수도 있음
            2. 클라이언트 개발자의 버그로 값이 누락될 수도 있다.
            3. 외부에서 값을 임의로 조작할 여부가 있다.
            4. DB에 값을 저장할 때 의도치 않은 값이 들어갈 수도 있다.
            5. 서버 개발자의 편안함을 위해?

            검증 컨벤션
            1. 응답값에 HashMap -> 응답 클래스를 만들어주는 것이 좋다.
            2. 여러 개의 에러 처리
            3. 세 번 이상의 반복적인 작업은 피해야한다.
                // 코드 && 개발에 관해 모든 것이 필요
                    // 세 번 이상이면 적어도 자동화를 고려해볼 것
    */
    @PostMapping("/posts")
    public Map<String, String> post(
            @RequestBody @Valid PostCreateDto params
//            ,BindingResult result        // 에러와 관련된 내용이 다 result에 담긴다
            ) throws Exception {

//        String title = params.getTitle();
//        if (title == null || title.equals("")) {
//            // error
//            /*
//            이런 식의 개발은 노가다, 빡셈이 따라온다.
//            누락될 염려도 있고 조건을 계속 추가해줘야 한다.
//            나도 별로 안좋아함
//             */
//            throw new Exception("title is null or null string");
//        }
//        String content = params.getContent();

//        if (result.hasErrors()) {
//            // google에 데이터 검증을 위해 "Junit5 jsonPath" 만 검색해도 나온다.
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            FieldError firstFieldError = fieldErrors.get(0);
//
//            String fieldName = firstFieldError.getField();              // 아까 냈던 에러 필드
//            String errorMessage = firstFieldError.getDefaultMessage();  // 에러 메세지
//
//            Map<String, String> errorMap = new HashMap<>();
//            errorMap.put(fieldName, errorMessage);
//            return errorMap;
//        }
        return Map.of();
    }

}
