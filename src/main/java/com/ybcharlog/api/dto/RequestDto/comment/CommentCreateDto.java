package com.ybcharlog.api.dto.RequestDto.comment;


import lombok.*;

import jakarta.validation.constraints.NotBlank;

@Data
@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CommentCreateDto {

    @NotBlank(message = "username input please")
    private String username;

    private String password;

    @NotBlank(message = "comment input please")
    private String content;

    private Integer secretStatus;

    private Integer display;
    private Integer isDeleted;

    private Long postId;

}
