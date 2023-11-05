package com.zerolab.bookecho.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewCreateDto {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private Integer starPoint;
    @NotNull
    private BookDto book;

    @Builder
    public ReviewCreateDto(String title, String content, Integer starPoint, BookDto bookDto) {
        this.title = title;
        this.content = content;
        this.starPoint = starPoint;
        this.book = bookDto;
    }
}