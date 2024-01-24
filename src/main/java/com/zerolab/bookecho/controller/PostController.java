package com.zerolab.bookecho.controller;

import com.zerolab.bookecho.dto.PostCreateDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class PostController {

    @GetMapping("/posts")
    public Map<String, String> get(@RequestBody @Valid PostCreateDto params){
        // 데이터를 검증하는 이유

        // 1. client 개발자가 깜박할 수 있다. -> 값을 안보낼 수 도 있다.
        // 2. client 버그로 값이 누락 될 수 있다.
        // 3. 외부의 다른 누군가가 값을 임의로 조작해서 보낼 수 있다.
        // 4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있다.
        // 5. 서버 개발자의 편안함을 위해서

        log.info("param={}",params.toString());
        return  Map.of();
    }
}
