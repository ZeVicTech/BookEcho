package com.zerolab.bookecho.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewCreateDto {

    private Long bookId;
    private String title;
    private String content;
    private int starPoint;

    @Builder
    public ReviewCreateDto(Long bookId, String title, String content, int starPoint) {
        this.bookId = bookId;
        this.title = title;
        this.content = content;
        this.starPoint = starPoint;
    }
}
