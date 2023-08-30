package com.zerolab.bookecho.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewEditDto {

    @NotNull
    private Long bookId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private Integer starPoint;

    @Builder
    public ReviewEditDto(Long bookId, String title, String content, Integer starPoint) {
        this.bookId = bookId;
        this.title = title;
        this.content = content;
        this.starPoint = starPoint;
    }
}
