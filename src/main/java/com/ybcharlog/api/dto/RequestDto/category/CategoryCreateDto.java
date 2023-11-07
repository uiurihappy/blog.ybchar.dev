package com.ybcharlog.api.dto.RequestDto.category;

import com.ybcharlog.api.domain.post.Post;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Data
@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CategoryCreateDto {

    @NotBlank(message = "Not blank Category depth1")
    private String depth1;
    private String depth2;
    private String depth3;
    private List<Post> posts;

}
