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
public class ReviewResponseDto {

    private Long id;
    private String title;
    private String content;
    private Integer starPoint;
    private LocalDateTime createDateTime;

    @JsonProperty(value="book")
    private BookResponseDto bookResponseDto;

    @JsonProperty(value="member")
    private MemberResponseDto memberResponseDto;

    @JsonProperty(value="comments")
    private List<CommentResponseDto> commentResponseDto;

    @Builder
    public ReviewResponseDto(Long id, String title, String content, Integer starPoint,
                             LocalDateTime createDateTime, BookResponseDto bookResponseDto,
                             MemberResponseDto memberResponseDto, List<CommentResponseDto> commentResponseDto) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.starPoint = starPoint;
        this.createDateTime = createDateTime;
        this.bookResponseDto = bookResponseDto;
        this.memberResponseDto = memberResponseDto;
        this.commentResponseDto = commentResponseDto;
    }

    public static ReviewResponseDto of(Review review){
        //리뷰의 댓글들을 뽑아서 CommentResponseDto로 변환
        List<CommentResponseDto> commentResponseDtos = review.getComments().stream()
                .map(CommentResponseDto::of)
                .collect(Collectors.toList());

        return ReviewResponseDto.builder()
                .id(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .starPoint(review.getStarPoint())
                .createDateTime(review.getCreateDateTime())
                .bookResponseDto(BookResponseDto.of(review.getBook()))
                .memberResponseDto(MemberResponseDto.of(review.getMember()))
                .commentResponseDto(commentResponseDtos)
                .build();
    }
}
