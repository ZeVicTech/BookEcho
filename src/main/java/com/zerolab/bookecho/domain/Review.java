package com.zerolab.bookecho.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    private String title;

    @Lob
    private String content;

    private Integer starPoint; // 1~5 점
    private LocalDateTime createDateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @JsonIgnore
    @OneToMany(mappedBy = "review",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Review(String title, String content, Integer starPoint, LocalDateTime createDateTime, Member member, Book book) {
        this.title = title;
        this.content = content;
        this.starPoint = starPoint;
        this.createDateTime = createDateTime;
        this.member = member;
        this.book = book;
    }

    //함수명
    public void editDetail(String title, String content, Integer starPoint) {
        this.title = title;
        this.content = content;
        this.starPoint = starPoint;
    }

    public void editBook(Book book){
        this.book = book;
    }
}
