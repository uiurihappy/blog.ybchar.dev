package com.ybcharlog.api.RequestDto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.max;

@Getter
@Setter
@Builder
@Data
public class CommentSearchDto {

    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size = 20;

    public long getOffset(Integer page, Integer size) {
        return (long) (max(1, page) - 1) * max(size, MAX_SIZE);
    }

}
