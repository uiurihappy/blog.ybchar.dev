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
    private String comment;

    @Builder
    public CommentEditDto(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

}
