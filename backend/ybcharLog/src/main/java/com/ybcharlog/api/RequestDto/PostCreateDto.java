package com.ybcharlog.api.RequestDto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@ToString
public class PostCreateDto {

    @NotBlank(message = "title input please")           // controller 에 넘어가기 전에 에러를 터트린다.
//    @NotNull
    private String title;

    @NotBlank(message = "content input please")
//    @NotNull
    private String content;

}
