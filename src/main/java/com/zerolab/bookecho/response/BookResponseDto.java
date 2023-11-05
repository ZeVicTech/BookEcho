package com.zerolab.bookecho.response;

import com.zerolab.bookecho.domain.Book;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookResponseDto {
    private String title;
    private String author;
    private String pubdate;
    private String publisher;
    private String image;
    private String isbn;

    @Builder
    public BookResponseDto(String title, String author, String pubdate, String publisher, String image, String isbn) {
        this.title = title;
        this.author = author;
        this.pubdate = pubdate;
        this.publisher = publisher;
        this.image = image;
        this.isbn = isbn;
    }

    public static BookResponseDto of(Book book){
        return BookResponseDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .pubdate(book.getPubdate())
                .publisher(book.getPublisher())
                .image(book.getImage())
                .isbn(book.getIsbn())
                .build();
    }
}
