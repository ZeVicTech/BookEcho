package com.zerolab.bookecho.controller;

import com.zerolab.bookecho.config.AppConfig;
import com.zerolab.bookecho.request.LoginDto;
import com.zerolab.bookecho.request.SignupDto;
import com.zerolab.bookecho.response.SessionResponse;
import com.zerolab.bookecho.service.AuthService;
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

    //서비스 게층에서 해결할지 레포지토리에서 해결할지 고민중
    private final AuthService authService;
    private final AppConfig appConfig;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody LoginDto loginDto){

        Long memberId = authService.signin(loginDto);

        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtkey());

        String jws = Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .setIssuedAt(new Date())
                .signWith(key)
                .compact();

        return new SessionResponse(jws);
    }

    @PostMapping("/auth/signup")
    public void signup(@RequestBody SignupDto signupDto){
        authService.signup(signupDto);
    }

}
