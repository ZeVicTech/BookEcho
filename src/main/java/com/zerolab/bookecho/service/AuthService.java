package com.zerolab.bookecho.service;

import com.zerolab.bookecho.crypto.PasswordEncoder;
import com.zerolab.bookecho.domain.Member;
import com.zerolab.bookecho.exception.AlreadyExistsLoginIdException;
import com.zerolab.bookecho.exception.InvalidLoginformation;
import com.zerolab.bookecho.repository.MemberRepository;
import com.zerolab.bookecho.request.Login;
import com.zerolab.bookecho.request.Signup;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //로그인
    public Long signin(Login login){

        Member member = memberRepository.findByLoginId(login.getLoginId())
                .orElseThrow(InvalidLoginformation::new);


        boolean matches = passwordEncoder.matches(login.getPassword(),member.getPassword());
        if(!matches){
            throw new InvalidLoginformation();
        }

        return member.getId();
    }

    //회원가입
    public void signup(Signup signup) {
        Optional<Member> memberOptional = memberRepository.findByLoginId(signup.getLoginId());
        if(memberOptional.isPresent()){
            throw new AlreadyExistsLoginIdException();
        }

        String encryptedPassword = passwordEncoder.encrypt(signup.getPassword());

        Member member = Member.builder()
                .nickName(signup.getNickName())
                .loginId(signup.getLoginId())
                .password(encryptedPassword)
                .build();

        memberRepository.save(member);
    }
}
