package com.ybcharlog.api.RequestDto.comment;


import lombok.*;

import javax.validation.constraints.NotBlank;

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
    private String comment;

    @NotBlank(message = "Require secretStatus")
    private Integer secretStatus;

}
