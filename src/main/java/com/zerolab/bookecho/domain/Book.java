package com.zerolab.bookecho.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Book {

    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    private String title;
    private String author;
    private String pubdate;
    private String publisher;
    private String image;
    private String isbn;

    @JsonIgnore
    @OneToMany(mappedBy = "book")
    private List<Review> books = new ArrayList<>();

    @Builder
    public Book(String title, String author, String pubdate, String publisher, String image, String isbn) {
        this.title = title;
        this.author = author;
        this.pubdate = pubdate;
        this.publisher = publisher;
        this.image = image;
        this.isbn = isbn;
    }

    public void edit(Book book){
        this.title = book.title;
        this.author = book.getAuthor();
        this.pubdate = book.getPubdate();
        this.publisher = book.getPublisher();
        this.image = book.getImage();
        this.isbn = book.isbn;
    }

    public void editImage(String image){
        this.image = image;
    }
}
