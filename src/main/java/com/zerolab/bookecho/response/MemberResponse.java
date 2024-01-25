package com.zerolab.bookecho.response;

import com.zerolab.bookecho.domain.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberResponse {
    private Long id;
    private String nickName;

    @Builder
    public MemberResponse(Long id, String nickName) {
        this.id = id;
        this.nickName = nickName;
    }

    public static MemberResponse of(Member member){

        return MemberResponse.builder()
                .id(member.getId())
                .nickName(member.getNickName())
                .build();
    }
}
