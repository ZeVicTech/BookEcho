package com.zerolab.bookecho.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zerolab.bookecho.domain.Review;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 홈화면 전달용 댓글 정보는 미포함
@Data
@NoArgsConstructor
public class ReviewResponse {
    private Long id;
    private String title;
    private String content;
    private Integer starPoint;
    private LocalDateTime createDateTime;

    @JsonProperty(value="book")
    private BookResponse bookResponse;

    @JsonProperty(value="member")
    private MemberResponse memberResponse;

    @Builder
    public ReviewResponse(Long id, String title, String content, Integer starPoint,
                          LocalDateTime createDateTime, BookResponse bookResponse,
                          MemberResponse memberResponse) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.starPoint = starPoint;
        this.createDateTime = createDateTime;
        this.bookResponse = bookResponse;
        this.memberResponse = memberResponse;
    }

    public static ReviewResponse of(Review review){

        return ReviewResponse.builder()
                .id(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .starPoint(review.getStarPoint())
                .createDateTime(review.getCreateDateTime())
                .bookResponse(BookResponse.of(review.getBook()))
                .memberResponse(MemberResponse.of(review.getMember()))
                .build();
    }
}
