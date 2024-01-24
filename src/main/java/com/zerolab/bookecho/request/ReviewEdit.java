package com.zerolab.bookecho.request;

import com.zerolab.bookecho.domain.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewEdit {

    @NotNull
    private Book book;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private Integer starPoint;

    @Builder
    public ReviewEdit(Book book, String title, String content, Integer starPoint) {
        this.book = book;
        this.title = title;
        this.content = content;
        this.starPoint = starPoint;
    }
}
