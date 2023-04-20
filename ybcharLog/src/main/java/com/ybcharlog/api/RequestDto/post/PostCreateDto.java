package com.ybcharlog.api.RequestDto.post;

import com.ybcharlog.api.exception.InvalidRequest;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostCreateDto {

    @NotBlank(message = "title input please")           // controller 에 넘어가기 전에 에러를 터트린다.
//    @NotNull
    private String title;

    @NotBlank(message = "content input please")
//    @NotNull
    private String content;

    private Integer viewCount;
    private Integer likeCount;
    private Integer display;
    private Integer isDeleted;

    public PostCreateDto changeTitle(String title) {
        return PostCreateDto.builder()
                .title(title)
                .build();
        /* builder의 장점
            - 가독성에 좋다. (값 생성에 대한 유연함)
            - 필요한 값만 받을 수 있다. (막 생성자 오버로딩하고 지저분하게 할 필요가 없음, 오버로딩 가능한 조건을 찾아볼 것!)
            - 객체의 불변성
         */
    }

    public void titleValidate() {
        if (title.contains("바보")) {
            throw new InvalidRequest("title", "제목에 바보를 포함시킬 수 없습니다.");
        }
    }

}
