package com.zerolab.bookecho.controller;

import com.zerolab.bookecho.config.AppConfig;
import com.zerolab.bookecho.request.Login;
import com.zerolab.bookecho.request.Signup;
import com.zerolab.bookecho.response.SessionResponse;
import com.zerolab.bookecho.service.AuthService;
import com.zerolab.bookecho.service.MemberService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;
    private final AppConfig appConfig;

    //로그인
    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login){

        //로그인 했을 때 닉네임과 jws키 반환
        Long memberId = authService.signin(login);
        String nickName = memberService.findNickNameById(memberId);

        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtkey());

        String jws = Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .setIssuedAt(new Date())
                .signWith(key)
                .compact();

        return new SessionResponse(nickName,jws);
    }

    //회원가입
    @PostMapping("/auth/signup")
    public void signup(@RequestBody Signup signup){
        authService.signup(signup);
    }

}
