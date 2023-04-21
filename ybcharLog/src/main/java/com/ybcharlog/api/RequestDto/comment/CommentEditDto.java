package com.ybcharlog.api.RequestDto.comment;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
public class CommentEditDto {

    @NotBlank(message = "Blanked username")
    private String username;

    @NotBlank(message = "Blanked comment")
    private String commentContent;

    private Integer display;
    private Integer isDeleted;

    @Builder
    public CommentEditDto(String username, String commentContent, Integer display, Integer isDeleted) {
        this.username = username;
        this.commentContent = commentContent;
        this.display = display;
        this.isDeleted = isDeleted;
    }

}
