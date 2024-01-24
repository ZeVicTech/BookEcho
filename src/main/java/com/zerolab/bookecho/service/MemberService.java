package com.zerolab.bookecho.service;

import com.zerolab.bookecho.domain.Member;
import com.zerolab.bookecho.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public String findNickNameById(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow();//todo 멤버 존재 X 예외 클래스 구현해야함

        return member.getNickName();
    }
}
