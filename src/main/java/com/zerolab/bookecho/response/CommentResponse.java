package com.zerolab.bookecho.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zerolab.bookecho.domain.Comment;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentResponse {

    private Long id;
    private String content;
    private LocalDateTime createDateTime;
    @JsonProperty(value="member")
    private MemberResponse memberResponse;

    @Builder
    public CommentResponse(Long id, String content, LocalDateTime createDateTime, MemberResponse memberResponse) {
        this.id = id;
        this.content = content;
        this.createDateTime = createDateTime;
        this.memberResponse = memberResponse;
    }

    public static CommentResponse of(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createDateTime(comment.getCreateDateTime())
                .memberResponse(MemberResponse.of(comment.getMember()))
                .build();
    }
}
