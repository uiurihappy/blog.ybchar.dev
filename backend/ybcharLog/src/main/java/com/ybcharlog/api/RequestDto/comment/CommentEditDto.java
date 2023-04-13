package com.ybcharlog.api.RequestDto.comment;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentEditDto {

    @NotBlank(message = "Blanked username")
    private String username;

    @NotBlank(message = "Blanked comment")
    private String commentContent;

    @Builder
    public CommentEditDto(String username, String commentContent) {
        this.username = username;
        this.commentContent = commentContent;
    }

}
