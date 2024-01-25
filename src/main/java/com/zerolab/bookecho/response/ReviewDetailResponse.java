package com.zerolab.bookecho.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zerolab.bookecho.domain.Review;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ReviewDetailResponse {

    private Long id;
    private String title;
    private String content;
    private Integer starPoint;
    private LocalDateTime createDateTime;

    @JsonProperty(value="book")
    private BookResponse bookResponse;

    @JsonProperty(value="member")
    private MemberResponse memberResponse;

    @JsonProperty(value="comments")
    private List<CommentResponse> commentResponse;

    @Builder
    public ReviewDetailResponse(Long id, String title, String content, Integer starPoint,
                                LocalDateTime createDateTime, BookResponse bookResponse,
                                MemberResponse memberResponse, List<CommentResponse> commentResponse) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.starPoint = starPoint;
        this.createDateTime = createDateTime;
        this.bookResponse = bookResponse;
        this.memberResponse = memberResponse;
        this.commentResponse = commentResponse;
    }

    public static ReviewDetailResponse of(Review review){
        //리뷰의 댓글들을 뽑아서 CommentResponse로 변환
        List<CommentResponse> commentResponses = review.getComments().stream()
                .map(CommentResponse::of)
                .collect(Collectors.toList());

        return ReviewDetailResponse.builder()
                .id(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .starPoint(review.getStarPoint())
                .createDateTime(review.getCreateDateTime())
                .bookResponse(BookResponse.of(review.getBook()))
                .memberResponse(MemberResponse.of(review.getMember()))
                .commentResponse(commentResponses)
                .build();
    }
}
