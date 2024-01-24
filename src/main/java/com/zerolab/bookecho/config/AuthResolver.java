package com.zerolab.bookecho.config;

import com.zerolab.bookecho.config.data.UserSession;
import com.zerolab.bookecho.exception.Unauthorized;
import com.zerolab.bookecho.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;
    private final AppConfig appConfig;

    //UserSession을 받을 경우 인증 인증로직 실행
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //UserSession을 받을 경우 인증 인증로직 실행
        return parameter.getParameterType().equals(UserSession.class);
    }

    //인증 로직
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        //헤더의 Authorization에 있는 jws값 추출
        String jws = webRequest.getHeader("Authorization");
        //jws가 없을 경우 권한 관련 예외처리
        if(jws == null || jws.equals("")){
            throw new Unauthorized();
        }

        //복호화 키 할당
        byte[] decodedKey = appConfig.getJwtkey();

        //jws를 복호화해서 memberId 추출
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(decodedKey)
                    .build()
                    .parseClaimsJws(jws);

            String memberId = claims.getBody().getSubject();
            return new UserSession(Long.valueOf(memberId));
        } catch (JwtException e){
            throw new Unauthorized();
        }
    }
}
