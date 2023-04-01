package com.ybcharlog.api.RequestDto;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class PostCreateDto {

    public String title;
    public String content;

    @Override
    public String toString() {
        return "PostCreateDto{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
