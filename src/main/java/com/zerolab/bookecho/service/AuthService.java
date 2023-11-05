package com.zerolab.bookecho.service;

import com.zerolab.bookecho.crypto.PasswordEncoder;
import com.zerolab.bookecho.domain.Member;
import com.zerolab.bookecho.exception.AlreadyExistsLoginIdException;
import com.zerolab.bookecho.exception.InvalidLoginformation;
import com.zerolab.bookecho.repository.MemberRepository;
import com.zerolab.bookecho.request.LoginDto;
import com.zerolab.bookecho.request.SignupDto;
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

    public Long signin(LoginDto loginDto){

        Member member = memberRepository.findByLoginId(loginDto.getLoginId())
                .orElseThrow(InvalidLoginformation::new);


        boolean matches = passwordEncoder.matches(loginDto.getPassword(),member.getPassword());
        if(!matches){
            throw new InvalidLoginformation();
        }

        return member.getId();
    }

    public void signup(SignupDto signupDto) {
        Optional<Member> memberOptional = memberRepository.findByLoginId(signupDto.getLoginId());
        if(memberOptional.isPresent()){
            throw new AlreadyExistsLoginIdException();
        }

        String encryptedPassword = passwordEncoder.encrypt(signupDto.getPassword());

        Member member = Member.builder()
                .nickName(signupDto.getNickName())
                .loginId(signupDto.getLoginId())
                .password(encryptedPassword)
                .build();

        memberRepository.save(member);
    }
}
