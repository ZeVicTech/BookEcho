package com.zerolab.bookecho.response;

import com.zerolab.bookecho.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String nickName;

    @Builder
    public MemberResponseDto(Long id, String nickName) {
        this.id = id;
        this.nickName = nickName;
    }

    public static MemberResponseDto of(Member member){

        return MemberResponseDto.builder()
                .id(member.getId())
                .nickName(member.getNickName())
                .build();
    }
}
