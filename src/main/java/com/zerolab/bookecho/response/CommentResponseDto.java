package com.zerolab.bookecho.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zerolab.bookecho.domain.Comment;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String content;
    private LocalDateTime createDateTime;
    @JsonProperty(value="member")
    private MemberResponseDto memberResponseDto;

    @Builder
    public CommentResponseDto(Long id, String content, LocalDateTime createDateTime, MemberResponseDto memberResponseDto) {
        this.id = id;
        this.content = content;
        this.createDateTime = createDateTime;
        this.memberResponseDto = memberResponseDto;
    }

    public static CommentResponseDto of(Comment comment){
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createDateTime(comment.getCreateDateTime())
                .memberResponseDto(MemberResponseDto.of(comment.getMember()))
                .build();
    }
}
