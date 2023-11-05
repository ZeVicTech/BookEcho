package com.zerolab.bookecho.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDto {
    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    private String publisher;
    @NotNull
    private String pubdate;
    @NotNull
    private String image;
    @NotNull
    private String isbn;

    @Builder
    public BookDto(String title, String author, String publisher, String pubdate, String image, String isbn) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.pubdate = pubdate;
        this.image = image;
        this.isbn = isbn;
    }
}
