package com.ybcharlog.api.RequestDto.comment;

import lombok.*;

import jakarta.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
public class CommentEditDto {

    @NotBlank(message = "Blanked username")
    private String username;

    @NotBlank(message = "Blanked comment")
    private String content;

    private Integer display;
    private Integer isDeleted;

    @Builder
    public CommentEditDto(String username, String content, Integer display, Integer isDeleted) {
        this.username = username;
        this.content = content;
        this.display = display;
        this.isDeleted = isDeleted;
    }

}
